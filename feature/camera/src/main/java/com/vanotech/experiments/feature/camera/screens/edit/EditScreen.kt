package com.vanotech.experiments.feature.camera.screens.edit

import android.Manifest
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.viewfinder.compose.MutableCoordinateTransformer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.SwitchCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vanotech.experiments.core.ui.components.BackButton
import com.vanotech.experiments.feature.camera.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.util.UUID


@Composable
internal fun EditScreen(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = EditViewModel.viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    EditScreen(
        camera = remember { SimpleCamera() },
        file = viewModel.captureFile,
        onDismissRequest = onDismissRequest,
        onSwitchCamera = { camera ->
            coroutineScope.launch {
                camera.switchCamera(context, lifecycleOwner)
            }
        },
        onUpdatePhoto = { camera, file ->
            coroutineScope.launch {
                camera.takePhoto(file)
                viewModel.setPhoto(file)
                onDismissRequest()
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
internal fun EditScreen(
    camera: SimpleCamera,
    file: File,
    onDismissRequest: () -> Unit,
    onSwitchCamera: (SimpleCamera) -> Unit,
    onUpdatePhoto: (SimpleCamera, File) -> Unit,
    modifier: Modifier = Modifier
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_camera_edit))
                },
                navigationIcon = {
                    BackButton(onClick = onDismissRequest)
                },
                actions = {
                    SwitchCameraIconButton(
                        onClick = { onSwitchCamera(camera) }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (cameraPermissionState.status.isGranted) {
                TakePictureFloatingActionButton(
                    onClick = { onUpdatePhoto(camera, file) }
                )
            }
        }
    ) { paddingValues ->
        if (cameraPermissionState.status.isGranted) {
            PermissionGrantedContent(
                camera = camera,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            PermissionDeniedContent(
                onRequestPermission = {
                    cameraPermissionState.launchPermissionRequest()
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
private fun PermissionGrantedContent(
    camera: SimpleCamera,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        camera.bindToCamera(context, lifecycleOwner)
    }

    var autofocusRequest by remember { mutableStateOf(UUID.randomUUID() to Offset.Unspecified) }
    val autofocusRequestId = autofocusRequest.first
    val showAutofocusIndicator = autofocusRequest.second.isSpecified
    val autofocusOffset = remember(autofocusRequestId) { autofocusRequest.second }

    if (showAutofocusIndicator) {
        LaunchedEffect(autofocusRequestId) {
            delay(1000)
            autofocusRequest = autofocusRequestId to Offset.Unspecified
        }
    }

    val currentSurfaceRequest by camera.surfaceRequest.collectAsState()
    currentSurfaceRequest?.also { surfaceRequest ->
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            val coordinateTransformer = remember { MutableCoordinateTransformer() }
            CameraXViewfinder(
                surfaceRequest = surfaceRequest,
                coordinateTransformer = coordinateTransformer,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(surfaceRequest, coordinateTransformer) {
                        detectTapGestures { gestureOffset ->
                            val surfaceOffset = with(coordinateTransformer) {
                                gestureOffset.transform()
                            }
                            camera.focusOnPoint(
                                surfaceRequest.resolution,
                                surfaceOffset.x,
                                surfaceOffset.y
                            )
                            autofocusRequest = UUID.randomUUID() to gestureOffset
                        }
                    }
            )

            AnimatedVisibility(
                visible = showAutofocusIndicator,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .offset { autofocusOffset.takeOrElse { Offset.Zero }.round() }
                    .offset((-24).dp, (-24).dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .border(2.dp, Color.White, CircleShape)
                        .size(48.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionDeniedContent(
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.permission_rationale_camera),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRequestPermission) {
            Text(stringResource(R.string.action_request_permission))
        }
    }
}

@Composable
private fun TakePictureFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = stringResource(R.string.action_take_photo)
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun SwitchCameraIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.SwitchCamera,
            contentDescription = stringResource(R.string.action_take_photo)
        )
    }
}
