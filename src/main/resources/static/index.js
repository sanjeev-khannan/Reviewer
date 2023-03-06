

// Sign In function 
function signIn(){

    var email = document.getElementById("email_data").value;
    var password = document.getElementById("password_data").value;

    const formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);

    fetch('/login', {
        method: 'POST',
        body: formData
      })
        .then(response => response.text())
        .then(data => {
          // Handle the response data
          alert(data)
        })
        .catch(error => {
          // Handle errors
          console.log('error', error)
        });
}

function register(){

    const form = document.querySelector("#create_form");
    const formData = new FormData(form);
    var object = {};
    formData.forEach(function(value, key){
        object[key] = value;
    }); 
    const json_string = JSON.stringify(object);
    fetch('/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
          },
        body: json_string
      })
      .then(response => {
        const statusCode = response.status;
        return response.text().then(data => ({ statusCode, data }));
        })
    .then(result => {
        // Access the status code and response data
        alert(result.data);
        if(result.statusCode==200){
            location.reload();
        }
    })
    .catch(error => {
        // Handle errors
        console.error('Error:', error);
    });
}

function openRegister(){
    fetch('/signup.html', {
        method: 'GET'
      })
        .then(response => response.text())
        .then(data => {
          // Handle the response data
          const page_body = document.getElementById("page_body");
          page_body.innerHTML = data;
        })
        .catch(error => {
          // Handle errors
          console.log('error', error)
        });
}






