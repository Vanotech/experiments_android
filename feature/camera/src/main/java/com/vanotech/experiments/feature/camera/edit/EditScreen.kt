package com.vanotech.experiments.feature.camera.edit

import androidx.camera.compose.CameraXViewfinder
import androidx.camera.viewfinder.compose.MutableCoordinateTransformer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vanotech.experiments.core.ui.NavigateBackButton
import com.vanotech.experiments.feature.camera.R
import kotlinx.coroutines.delay
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
internal fun EditScreen(
    navController: NavController,
    viewModel: EditViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_camera_edit))
                },
                navigationIcon = {
                    NavigateBackButton(navController = navController)
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (cameraPermissionState.status.isGranted) {
                FloatingActionButton(
                    onClick = {
                        viewModel.takePhoto()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = stringResource(R.string.action_take_photo)
                    )
                }
            }
        }
    ) { paddingValues ->
        if (cameraPermissionState.status.isGranted) {
            EditGrantedScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            EditDeniedScreen(
                cameraPermissionState = cameraPermissionState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            )
        }
    }
}

@Composable
private fun EditGrantedScreen(
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context, lifecycleOwner)
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

    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    surfaceRequest?.let { request ->
        val coordinateTransformer = remember { MutableCoordinateTransformer() }
        CameraXViewfinder(
            surfaceRequest = request,
            coordinateTransformer = coordinateTransformer,
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures { gestureOffset ->
                    val surfaceOffset = with(coordinateTransformer) {
                        gestureOffset.transform()
                    }
                    viewModel.focusOnPoint(surfaceOffset)
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun EditDeniedScreen(
    cameraPermissionState: PermissionState,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.permission_rationale_camera),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
            Text(stringResource(R.string.action_request_permission))
        }
    }
}