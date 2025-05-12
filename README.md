<h1 align="center">📱 PetCare App</h1>

<p align="center">
  Aplicação mobile desenvolvida em <strong>Kotlin</strong> com <strong>Jetpack Compose</strong>, permitindo que clientes de petshops agendem serviços e gerenciem seus pets.
</p>

<h2>🚀 Funcionalidades</h2>
<ul>
  <li>✔️ Cadastro de pets</li>
  <li>✔️ Agendamento de serviços</li>
  <li>✔️ Pagamento via PIX (Mercado Pago)</li>
  <li>✔️ Histórico de agendamentos</li>
</ul>

<h2>🛠 Tecnologias Utilizadas</h2>
<ul>
  <li><strong>Kotlin</strong> – Linguagem principal</li>
  <li><strong>Jetpack Compose</strong> – UI declarativa</li>
  <li><strong>Retrofit</strong> – Consumo da API</li>
  <li><strong>Hilt</strong> – Injeção de dependências</li>
  <li><strong>Navigation Component</strong> – Navegação entre telas</li>
</ul>

<h2>⚠️ Aviso importante</h2>
<p>
  A aplicação consome dados de uma API. Certifique-se de que o <strong>PetCare Backend</strong> está em execução localmente ou hospedado em algum servidor. 
</p>
<p>
  Edite a URL base da API no arquivo <code>src/main/java/com/example/petcare_app/data/network/RetrofitInstance.kt</code> para apontar para o backend correto:
</p>
<pre><code>
// Exemplo:
private const val BASE_URL = "http://192.168.0.100:8080/" // ajuste conforme necessário
</code></pre>

<h2>💻 Como rodar a aplicação localmente</h2>

<h3>1. Clone o repositório:</h3>
<pre><code>git clone https://github.com/seuusuario/petcare-app.git</code></pre>

<h3>2. Abra no Android Studio:</h3>
<p>
  Vá em <strong>File > Open</strong> e selecione a pasta do projeto clonado.
</p>

<h3>3. Sincronize as dependências:</h3>
<p>
  O Android Studio fará isso automaticamente, mas você pode forçar a sincronização com <strong>File > Sync Project with Gradle Files</strong>.
</p>

<h3>4. Edite a baseURL:</h3>
<p>
  Altere o valor da baseURL no arquivo <code>RetrofitInstance.kt</code> para apontar para o backend que está rodando.
</p>

<h3>5. Rode o backend:</h3>
<p>
  É necessário que o backend esteja em execução para que o app funcione corretamente. Siga as instruções do repositório do <strong>PetCare Backend</strong> para executá-lo.
</p>

<h3>6. Execute o app:</h3>
<p>
  Escolha um dispositivo ou emulador e clique no botão <strong>Run</strong> (ícone de play ▶️) para compilar e iniciar a aplicação.
</p>

<h2>📦 Como fazer o Deploy</h2>

<h3>1. Gere o APK:</h3>
<p>Para compilar a versão final da aplicação, vá em:</p>
<pre><code>Build > Build Bundle(s) / APK(s) > Build APK(s)</code></pre>

<h3>2. Localize o APK:</h3>
<p>Após a build, uma notificação aparecerá com a opção <strong>locate</strong> para abrir a pasta onde o APK está salvo.</p>
  
<h3>3. Instale no celular:</h3>
<p>Transfira o APK para seu dispositivo Android e abra-o para instalar. Certifique-se de que a opção "Permitir instalação de fontes desconhecidas" esteja ativada no seu celular.</p>

<h2>🔒 Sistema de Login</h2>
<p>
  O aplicativo possui um sistema de autenticação onde o usuário pode:
</p>
<ul>
  <li>Realizar cadastro</li>
  <li>Efetuar login</li>
  <li>Manter sessão ativa para agendamentos e histórico</li>
</ul>

<h2>📢 Observações Finais</h2>
<ul>
  <li>Certifique-se de que o backend está funcionando corretamente antes de testar as funcionalidades que envolvem requisições à API.</li>
  <li>Se estiver usando um emulador, use <code>10.0.2.2</code> ao invés de <code>localhost</code> na baseURL.</li>
</ul>
