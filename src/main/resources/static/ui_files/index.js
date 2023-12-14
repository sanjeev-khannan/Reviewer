
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

					fetch('/ui_files/account_dropdown.html', {
						method: 'GET'
					})
						.then(response => response.text())
						.then(data => { div_body.innerHTML = data; })
						.catch(error => console.error(error));

					// div_body.innerHTML = "My Account<br>Preferences<br>Modify Account<br>View Account Details<br>Customer Support<br><br><button onclick=\"logout()\" class=\"btn btn-primary\">Sign Out</button>"
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
			loadSignUpJS();
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
		console.log(supportTickets);
		var htmlBody = "";
		for (var i = 0; i < supportTickets.length; i++) {
			const subject = supportTickets[i].subject;
			const ticket_id = supportTickets[i].ticket_id;
			subjects.push({ subject: subject, ticket: ticket_id });
		}
		console.log(subjects);
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

function viewMyAccount() {
	window.scrollTo(0, 0);
	fetch('./ui_files/my_account.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			// Handle the response data00
			const page_body = document.getElementById("page_body");
			page_body.innerHTML = data;
			setNavLinks();
			loadMyAccount('view')
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});
}

function loadMyAccount(option) {
	if (option == "view") {
		fetch('./ui_files/modify_account.html', {
			method: 'GET'
		})
			.then(response => response.text())
			.then(async data => {
				// Handle the response data
				const page_body = document.getElementById("account_section");
				page_body.innerHTML = data;
				const user = await getUser();
				document.getElementById('register_firstName').value = user.firstName;
				document.getElementById('register_lastName').value = user.lastName;
				document.getElementById('register_addressLine1').value = user.addressLine1;
				document.getElementById('register_addressLine2').value = user.addressLine2;
				document.getElementById('register_city').value = user.city;
				document.getElementById('register_state').value = user.state;
				document.getElementById('register_zipCode').value = user.zipCode;
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});
	}

	else if (option == "delete") {
		fetch('./ui_files/delete_account.html', {
			method: 'GET'
		})
			.then(response => response.text())
			.then(async data => {
				// Handle the response data
				const page_body = document.getElementById("account_section");
				page_body.innerHTML = data;
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});
	}
}

function getUser() {
	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);

	if (token != null) {
		return fetch('/getuserbytoken', {
			method: 'POST',
			headers: headers
		})
			.then(response => {
				const statusCode = response.status;
				return response.text().then(data => ({ statusCode, data }));
			})
			.then(result => {
				// Handle the response data
				const data = JSON.parse(result.data);
				return data;
			});
	}
}

function saveDetails() {

	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	headers.append('Content-Type', 'application/json');

	const form = document.querySelector("#modify_form");
	const formData = new FormData(form);
	console.log(formData);
	var object = {};
	formData.forEach(function (value, key) {
		object[key] = value;
	});
	const json_string = JSON.stringify(object);
	console.log(json_string)

	fetch('/account/saveDetails', {
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
			signInUser();
			viewMyAccount();
		})
		.catch(error => {
			// Handle errors
			console.error('Error:', error);
		});
}

function loadSignUpJS() {
	const passwordInput = document.getElementById("register_password");
	const passwordError = document.getElementById("password-error");

	function validatePassword() {
		const password = passwordInput.value;
		let errorMessage = "";

		if (!/(?=.*[a-z])/.test(password)) {
			errorMessage += "At lease one lower case letter. <br>";
		}
		if (!/(?=.*[A-Z])/.test(password)) {
			errorMessage += "At lease one upper case letter. <br>";
		}
		if (!/(?=.[!@#$%^&()_+])/.test(password)) {
			errorMessage += "At lease one special letter. <br>";
		}
		if (!/(?=.{6,})/.test(password)) {
			errorMessage += "The length must be at least 6 characters. <br>";
		}

		if (errorMessage) {
			passwordError.innerHTML = errorMessage;
			passwordError.style.display = "block";
		} else {
			passwordError.style.display = "none";
		}
	};
	passwordInput.addEventListener("input", validatePassword);

	const emailInput = document.getElementById("register_email");
	const emailError = document.getElementById("email-error");

	function validateEmail() {
		const email = emailInput.value;
		let errorMessage = "";

		if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
			errorMessage += "Please enter a valid email address";
			emailError.innerHTML = errorMessage;
			emailError.style.display = "block";
		} else {
			emailError.style.display = "none";
		}

	}
	emailInput.addEventListener("input", validateEmail);

	const mobileNumberInput = document.getElementById("register_mobileNumber");
	const mobileNumberError = document.getElementById("mobileNumber-error");

	function validateMobileNumber() {
		const mobileNumber = mobileNumberInput.value;
		let errorMessage = "";

		if (!/^\d{10}$/.test(mobileNumber)) {
			errorMessage += "Please enter a valid Mobile Number";
			mobileNumberError.innerHTML = errorMessage;
			mobileNumberError.style.display = "block";
		} else {
			mobileNumberError.style.display = "none";
		}

	}
	mobileNumberInput.addEventListener("input", validateMobileNumber);

}

function updateButton() {
	const checkbox = document.getElementById('delete_consent');
	const button = document.getElementById('delete_button');
	button.disabled = !checkbox.checked;
}

function deleteAccount() {

	const token = localStorage.getItem('auth-token');

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	headers.append('Content-Type', 'application/json');

	fetch('/account/deleteaccount', {
		method: 'DELETE',
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
				alert(data['message'])
				localStorage.removeItem('auth-token');
				location.reload();
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


function selectPrice(priceValue) {
	// Set the selected price value in the hidden form field
	for (var i = 1; i < 5; i++) {
		const clsList = document.getElementById("price_o" + i).classList;
		clsList.remove('fas');
		clsList.add('far');
	}

	previousValue = document.getElementById("selected_price").value;
	if (previousValue != priceValue) {
		const clsList = document.getElementById("price_" + priceValue).classList;
		clsList.remove('far');
		clsList.add('fas');
		document.getElementById("selected_price").value = priceValue;
	}
	else {
		document.getElementById("selected_price").value = "";
	}
	fillSearchResults();
}
function selectSort(sortValue) {
	// Set the selected price value in the hidden form field
	for (var i = 1; i < 6; i++) {
		const clsList = document.getElementById("sort_o" + i).classList;
		clsList.remove('fas');
		clsList.add('far');
	}
	previousValue = document.getElementById("selected_sorting").value;
	if (previousValue != sortValue) {
		const clsList = document.getElementById("sort_" + sortValue).classList;
		clsList.remove('far');
		clsList.add('fas');
		document.getElementById("selected_sorting").value = sortValue;
	}
	else {
		document.getElementById("selected_sorting").value = "";
	}

	fillSearchResults();
}

function selectRating(ratingValue) {
	// Set the selected price value in the hidden form field
	for (var i = 1; i < 6; i++) {
		document.getElementById("rating_o" + i).style = "";
	}
	previousValue = document.getElementById("selected_rating").value;
	if (previousValue != ratingValue) {
		document.getElementById("rating_o" + ratingValue).style = "color:midnightblue";
		document.getElementById("selected_rating").value = ratingValue;
	}
	else {
		document.getElementById("selected_rating").value = "";
	}
	fillSearchResults();
}

function resetFilters() {

	for (var i = 1; i < 6; i++) {
		const clsList = document.getElementById("sort_o" + i).classList;
		clsList.remove('fas');
		clsList.add('far');
	}

	for (var i = 1; i < 5; i++) {
		const clsList = document.getElementById("price_o" + i).classList;
		clsList.remove('fas');
		clsList.add('far');
	}

	document.getElementById("selected_sorting").value = "";
	document.getElementById("selected_price").value = "";
	document.getElementById("selected_rating").value = "";

	fillSearchResults();
}

// Handle suggestion click
$(document).on('click', '.suggestion', function () {
	const selectedSuggestion = $(this).text();
	$('#search_box').val(selectedSuggestion);
	$('#suggestionDropdown').hide();
});

function fillSearchSuggestions() {

	var matchingSuggestions = [];

	searchKeywords().then(result => {
		result.forEach(venue => {
			matchingSuggestions.push(venue['venueName'])
		});
		var dropdown = $('#suggestionDropdown');
		dropdown.empty();

		if (matchingSuggestions.length > 0) {
			// Add matching suggestions to the dropdown
			matchingSuggestions.slice(0, 5).forEach(suggestion => {
				dropdown.append(`<p class="suggestion">${suggestion}</p>`);
			})

			dropdown.show();
			// Show the dropdown
		} else {
			// Hide the dropdown if there are no matching suggestions
			dropdown.hide();
		}
	});

}


function viewUsagePatterns() {
	fetch('./ui_files/usage_pattern.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(async data => {
			// Handle the response data
			const page_body = document.getElementById("account_section");
			page_body.innerHTML = data;
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});
}


function changeOption(option) {
	document.getElementById('selectedOption').innerText = option;
}