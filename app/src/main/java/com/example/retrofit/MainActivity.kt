package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.ui.theme.RetrofitTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        MeuTexto()
                        /*runBlocking {
                            //val texto = restapi.getPhotos()
                            val texto = restapi2.getMensagem()
                            Log.d("API", "Mensagem: $texto")
                            Log.d("API", "Contagem: ${restapi2.getContagem()}")
                        }*/
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitTheme {
            Greeting("Android")
    }
}



class RespostaViewModel: ViewModel() {
    var resposta by mutableStateOf<String?>(null)
    var mensagem by mutableStateOf<String?>(null)
    var contagem by mutableStateOf<Int?>(null)
    init {
        fetchResposta()
        fetchMensagem()
        fetchContagem()
    }

    private fun fetchResposta() {
        viewModelScope.launch {
            try {
                resposta = restapi.getPhotos()
            } catch (e: Exception) {
                resposta = "Erro: ${e.localizedMessage}"
                Log.d("API", "$resposta")
            }
        }

    }


    private fun fetchMensagem() {
        viewModelScope.launch {
            try {
                mensagem = restapi2.getMensagem()
            } catch (e: Exception) {
                mensagem = "Erro: ${e.localizedMessage}"
                Log.d("API", "$mensagem")
            }
        }

    }

    private fun fetchContagem() {
        viewModelScope.launch {
            try {
                contagem = restapi2.getContagem()
            } catch (e: Exception) {
                //contagem = "Erro: ${e.localizedMessage}"
                Log.d("API", "Erro: ${e.localizedMessage}")
            }
        }

    }



}

@Composable
fun MeuTexto(viewModel: RespostaViewModel = viewModel()) {
    val respostaVm = viewModel.resposta
    val mensagemVm = viewModel.mensagem
    val contagemVm = viewModel.contagem
    when {
        //respostaVm != null -> {
        //    Text(text = respostaVm)
        //}
        mensagemVm != null -> {
            Text(text = mensagemVm)
        }
        contagemVm != null -> {
            Text(text = contagemVm.toString())
        }

    }

}