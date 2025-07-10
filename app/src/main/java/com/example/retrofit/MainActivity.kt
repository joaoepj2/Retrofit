package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.ui.theme.RetrofitTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



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
                        Tela1()
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
    init {
        fetchResposta()
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
}

class MensagemViewModel: ViewModel() {
    var mensagem by mutableStateOf<String?>(null)
    init {
        fetchMensagem()
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
}


class ContagemViewModel: ViewModel() {
    private var _contagem = MutableStateFlow<Int?>(null)
    val contador: StateFlow<Int?> = _contagem

    init {
        fetchContagem()
    }

    fun fetchContagem() {
        viewModelScope.launch {
            try {
                _contagem.value = restapi2.getContagem()
            } catch (e: Exception) {
                //contagem = "Erro: ${e.localizedMessage}"
                Log.d("API", "Erro: ${e.localizedMessage}")
            }
        }
    }
}

@Composable
fun Resposta(viewModel: RespostaViewModel = viewModel()) {
    val respostaVm = viewModel.resposta
    when {
        respostaVm != null -> {
            Text(text = respostaVm)
        }
    }
}

@Composable
fun Mensagem(viewModel: MensagemViewModel = viewModel()) {
    val respostaVm = viewModel.mensagem
    when {
        respostaVm != null -> {
            Text(text = respostaVm)
        }
    }
}

@Composable
fun Contagem(viewModel: ContagemViewModel = viewModel()) {
    val valor by viewModel.contador.collectAsState()

    Text(text = "$valor")

    Button(onClick = { viewModel.fetchContagem() }) {
        Text(text = "Contar")
    }
}

@Composable
fun Tela1() {

    Column {
        Mensagem()
        Contagem()

    }
}