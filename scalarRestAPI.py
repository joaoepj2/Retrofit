from flask import Flask, request

app = Flask(__name__)

mensagem = "Olá, mundo!"
contador = 0

# GET simples retorna uma string
@app.route("/mensagem", methods=["GET"])
def get_mensagem():
    return mensagem  # tipo: string

# GET retorna um número inteiro
@app.route("/contador", methods=["GET"])
def get_contador():
    global contador
    contador += 1
    return str(contador)  # tipo: int convertido para string

# POST aceita uma string e responde com OK ou ERRO
@app.route("/enviar", methods=["POST"])
def enviar_dado():
    dado = request.get_data(as_text=True)
    if dado:
        return "OK"  # tipo: string
    return "ERRO", 400  # tipo: string

# GET retorna um valor booleano
@app.route("/ativo", methods=["GET"])
def is_ativo():
    return "true"  # tipo: boolean (representado como string)
    
if __name__ == "__main__":
    app.run(debug=True)
