
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
					
					div_body = document.getElementById("account_dropdown")
					div_body.innerHTML = "My Account<br>Preferences<br>Modify Account<br>View Account Details<br>Customer Support<br><br><button onclick=\"logout()\" class=\"btn btn-primary\">Sign Out</button>"
				}
				else {
					alert(data["message"])
					console.log(data)
				}
				setNavLinks();
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
	formData.forEach(function (value, key) {
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

function setNavLinks() {
	const navLinks = document.querySelectorAll('.nav-link');

	// Add event listener to each anchor tag
	navLinks.forEach(function (link) {
		link.addEventListener('click', function (event) {
			// Prevent default anchor tag behavior
			event.preventDefault();

			// Remove 'active' class from previously active element
			const activeLink = document.querySelector('.nav-link.active');
			if (activeLink) {
				activeLink.classList.remove('active');
			}

			// Add 'active' class to clicked element
			link.classList.add('active');

			// Your code to execute on click goes here
		});
	});
}

function viewCustomerSupport() {
	window.scrollTo(0, 0);
	fetch('./ui_files/customersupport.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			// Handle the response data
			const page_body = document.getElementById("page_body");
			page_body.innerHTML = data;
			setNavLinks();
			loadCustomerSupport('new');
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});
}

function loadCustomerSupport(option) {
	if (option == "new") {
		fetch('./ui_files/create_ticket.html', {
			method: 'GET'
		})
			.then(response => response.text())
			.then(data => {
				// Handle the response data
				const page_body = document.getElementById("ticket_section");
				page_body.innerHTML = data;
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});
	}
	else if (option == "myt") {

		fetch('./ui_files/view_all_tickets.html', {
			method: 'GET'
		})
			.then(response => response.text())
			.then(data => {
				// Handle the response data
				getMyTickets().then(result => {
					var template = Handlebars.compile(data);
					const htmldata = template({ subjects: result });
					const page_body = document.getElementById("ticket_section");
					page_body.innerHTML = htmldata;
				});
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});


	}
}

async function getMyTickets() {

	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);

	try {
		const response = await fetch('/support/getmytickets', {
			method: 'POST',
			headers: headers
		});

		const statusCode = response.status;
		const data = await response.text();
		const result_1 = ({ statusCode, data });
		// Access the status code and response data
		let subjects = [];
		const supportTickets = JSON.parse(result_1.data);
		var htmlBody = "";
		for (var i = 0; i < supportTickets.length; i++) {
			const subject = supportTickets[i].subject;
			const ticket_id = supportTickets[i].ticket_id;
			console.log(supportTickets[i]);
			subjects.push({ subject: subject, ticket: ticket_id });
		}
		return subjects;
	} catch (error) {
		// Handle errors
		console.error('Error:', error);
		return [];
	}
}

async function loadTicket(ticket) {

	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	let curr_ticket = null;
	try {
		const response = await fetch('/support/getticketbyid?ticket_id=' + ticket, {
			method: 'POST',
			headers: headers,
		});

		const statusCode = response.status;
		const data = await response.text();
		curr_ticket = JSON.parse(data);
	} catch (error) {
		// Handle errors
		console.error('Error:', error);
		return [];
	}

	fetch('./ui_files/view_tickets.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			const page_body = document.getElementById("ticket_section");
			const template = Handlebars.compile(data);
			page_body.innerHTML = template(curr_ticket);
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});

}


function submitTicket() {

	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	headers.append('Content-Type', 'application/json');

	const form = document.querySelector("#ticket-form");
	const formData = new FormData(form);
	console.log(formData);
	var object = {};
	formData.forEach(function (value, key) {
		object[key] = value;
	});
	const json_string = JSON.stringify(object);
	console.log(json_string)

	fetch('/support/createticket', {
		method: 'POST',
		headers: headers,
		body: json_string
	})
		.then(response => {
			const statusCode = response.status;
			return response.text().then(data => ({ statusCode, data }));
		})
		.then(result => {
			// Access the status code and response data
			let message = JSON.parse(result.data)['message'];
			alert(message);
			if (result.statusCode == 200) {
				viewCustomerSupport();
				loadCustomerSupport("myt");
			}
			else {
				viewCustomerSupport();
				loadCustomerSupport("new");
			}
		})
		.catch(error => {
			// Handle errors
			console.error('Error:', error);
		});
}

function handleClick(element) {
	console.log(element.getName());
}