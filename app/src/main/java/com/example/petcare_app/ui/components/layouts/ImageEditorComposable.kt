package com.example.petcare_app.ui.components.layouts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.petcare_app.R
import com.example.petcare_app.data.viewmodel.EditUserViewModel
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

//@Preview(showBackground = true)
//@Composable
//private fun ImageEditorComposablePreview(
//) {
//    ImageEditorComposable("")
//}

@Composable
fun ImageEditorComposable(
    editUserViewModel: EditUserViewModel,
    imageUrl : String?
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para receber a imagem cortada
    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val croppedUri = result.data?.let { UCrop.getOutput(it) }
        if (croppedUri != null) {
            imageUri = croppedUri // Atualiza a imagem recortada
            editUserViewModel.uploadUserImage(context, croppedUri) // Faz o upload
        }
    }

    // Launcher para selecionar imagem
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { startCrop(context, it, cropLauncher::launch) }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Exibir a foto do usuário ou um ícone de perfil
        val painterImg: Painter = when {
            imageUri != null -> rememberAsyncImagePainter(imageUri)
            !imageUrl.isNullOrBlank() -> rememberAsyncImagePainter("${imageUrl}?timestamp=${System.currentTimeMillis()}")
            else -> painterResource(R.drawable.ic_no_profile_picture)
        }



        Image(
            painter = painterImg,
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Botão de Atualizar Foto
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(255, 210, 105),
                    contentColor = Color(32, 105, 131)
                )
            ) {
                Icon(painter = painterResource(R.drawable.ic_upload_img), contentDescription = null, tint = Color(32, 105, 131))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Atualizar Foto", color = Color(32, 105, 131))
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Botão de Remover Foto
            Button(
                onClick = { imageUri = null },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(32, 105, 131),
                    contentColor = Color.White
                )
            ) {
                Icon(painter = painterResource(R.drawable.ic_trash_can), contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Remover Foto", color = Color.White)
            }
        }
    }
}

fun startCrop(context: Context, sourceUri: Uri, cropLauncher: (Intent) -> Unit) {
    val activity = context as? Activity ?: return

    val croppedFile = File(context.cacheDir, "cropped_images")
    if (!croppedFile.exists()) croppedFile.mkdirs()

    val destinationUri = Uri.fromFile(File(croppedFile, "${UUID.randomUUID()}.jpg"))

    val uCrop = UCrop.of(sourceUri, destinationUri)
        .withAspectRatio(1f, 1f)
        .withMaxResultSize(512, 512)

    cropLauncher(uCrop.getIntent(activity))
}

fun createMultipartFromUri(context: Context, uri: Uri, name: String = "file"): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "upload_image.jpg")
    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}


