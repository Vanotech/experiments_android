package com.vanotech.experiments.feature.camera.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.vanotech.experiments.feature.camera.CameraNavGraph
import com.vanotech.experiments.feature.camera.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_camera_home))
                },
                actions = {
                    TakePictureIconButton(viewModel = viewModel)
                    PickVisualMediaIconButton(viewModel = viewModel)

                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            TakePictureFloatingActionButton(
                navController = navController,
            )
        }
    ) { paddingValues ->
        CaptureContent(
            viewModel = viewModel,
            paddingValues = paddingValues
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CaptureContent(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState = viewModel.uiState.collectAsState().value

        val context = LocalContext.current
        val uri = uiState.uri.collectAsState(null).value
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
    navController: NavController,
) {
    FloatingActionButton(
        onClick = {
            CameraNavGraph.navigateToEdit(navController)
        },
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = stringResource(R.string.action_take_photo)
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TakePictureIconButton(viewModel: HomeViewModel) {
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.updatePhoto(viewModel.uri)
        }
    }

    val takePicturePermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ) { granted ->
        if (granted) {
            takePictureLauncher.launch(viewModel.uri)
        }
    }

    IconButton(
        onClick = {
            takePicturePermissionState.launchPermissionRequest()
        }
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = stringResource(R.string.action_take_photo)
        )
    }
}

@Composable
private fun PickVisualMediaIconButton(viewModel: HomeViewModel) {
    val pickVisualMediaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.also {
            viewModel.updatePhoto(it)
        }
    }

    IconButton(
        onClick = {
            val input = PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
            pickVisualMediaLauncher.launch(input)
        }) {
        Icon(
            imageVector = Icons.Default.AddPhotoAlternate,
            contentDescription = stringResource(R.string.action_pick_photo)
        )
    }
}