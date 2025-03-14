// URL padrão que vai rodar o front nesse primeiro momento
const url = "http://localhost:8080/task/user/1";

//Função para esconder o ícone de loading
function hideLoader() {
  document.getElementById("loading").style.display = "none";
}



function show(tasks) {

  let tab = `<thead>
            <th scope="col">#</th>
            <th scope="col">ID Usuario</th>
            <th scope="col">Nome do Usuario</th>
            <th scope="col">Descrição</th>
        </thead>`;

  for (let task of tasks) {
    tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.user.id}</td>
                <td>${task.user.username}</td>
                <td>${task.description}</td>
            </tr>
        `;
  }


  document.getElementById("tasks").innerHTML = tab;



}

async function getAPI(url){

    const response = await fetch (url, { method: "GET"});


    var data = await response.json();
    console.log(data);

    if(response)  hideLoader();
    show(data)

}


getAPI(url);