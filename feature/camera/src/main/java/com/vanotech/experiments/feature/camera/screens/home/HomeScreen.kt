package com.vanotech.experiments.feature.camera.screens.home

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.vanotech.experiments.feature.camera.CameraNavGraph
import com.vanotech.experiments.feature.camera.R
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        displayUri = uiState.uri,
        captureUri = viewModel.captureUri,
        onNavigateToCamera = { CameraNavGraph.navigateToEdit(navController) },
        onUpdatePhoto = {
            coroutineScope.launch {
                viewModel.setPhoto(it)
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    displayUri: Uri?,
    captureUri: Uri,
    modifier: Modifier = Modifier,
    onNavigateToCamera: () -> Unit,
    onUpdatePhoto: (Uri) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_camera_home))
                },
                actions = {
                    TakePictureIconButton(
                        uri = captureUri,
                        onTakePicture = onUpdatePhoto
                    )
                    PickVisualMediaIconButton(onPickVisualMedia = onUpdatePhoto)
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            TakePictureFloatingActionButton(onClick = onNavigateToCamera)
        }
    ) { paddingValues ->
        CaptureContent(
            uri = displayUri,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun CaptureContent(
    uri: Uri?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val placeholder = rememberVectorPainter(Icons.Default.Person)
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(uri)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.surfaceContainer, CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = placeholder,
            fallback = placeholder
        )
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

@Composable
private fun PickVisualMediaIconButton(
    onPickVisualMedia: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val pickVisualMediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.also {
            onPickVisualMedia(it)
        }
    }

    IconButton(
        onClick = {
            val input = PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
            pickVisualMediaLauncher.launch(input)
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.AddPhotoAlternate,
            contentDescription = stringResource(R.string.action_pick_photo)
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TakePictureIconButton(
    uri: Uri,
    onTakePicture: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onTakePicture(uri)
        }
    }

    val takePicturePermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    ) { granted ->
        if (granted) {
            takePictureLauncher.launch(uri)
        }
    }

    IconButton(
        onClick = {
            takePicturePermissionState.launchPermissionRequest()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = stringResource(R.string.action_take_photo)
        )
    }
}
