
signInUser()

function authenticate() {

	var email = document.getElementById("email_data").value;
	var password = document.getElementById("password_data").value;

	var object = {
		"username": email,
		"password": password
	};
	const json_string = JSON.stringify(object);

	fetch('/authenticate', {
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
			// Handle the response data
			if (result.statusCode == 200) {
				const jwt = JSON.parse(result.data)
				localStorage.setItem('auth-token', jwt['token']);
				location.reload();
			}
			else {
				const data = JSON.parse(result.data)
				alert(data['message']);
			}
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});
}



// Sign In function 
function signInUser() {

	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);

	if (token != null) {
		fetch('/getuserbytoken', {
			method: 'POST',
			headers: headers
		})
			.then(response => {
				const statusCode = response.status;
				return response.text().then(data => ({ statusCode, data }));
			})
			.then(result => {
				// Handle the response data
				const data = JSON.parse(result.data)
				if (result.statusCode == 200) {
					var div_body = document.getElementById("account_dropdown_icon")
					div_body.innerHTML = "<img width=\"20px\" src=\"./images/user.png\"/>  " + data["firstName"]
					console.log(data)

					div_body = document.getElementById("account_dropdown")
					div_body.innerHTML = "My Account<br>Preferences<br>Modify Account<br>View Account Details<br>Customer Support<br><br><button onclick=\"logout()\" class=\"btn btn-primary\">Sign Out</button>"
				}
				else {
					alert(data["message"])
					console.log(data)
				}
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});
	}
}

function register() {

	const form = document.querySelector("#create_form");
	const formData = new FormData(form);
	var object = {};
	formData.forEach(function(value, key) {
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
			if (result.statusCode == 200) {
				location.reload();
			}
		})
		.catch(error => {
			// Handle errors
			console.error('Error:', error);
		});
}

function logout() {
	localStorage.removeItem('auth-token');
	location.reload();
}

function openRegister() {
	fetch('./ui_files/signup.html', {
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
