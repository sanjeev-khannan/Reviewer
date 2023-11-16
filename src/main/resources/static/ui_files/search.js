
fillSearchResults();

async function searchPhrase() {

	const searchPhrase = document.getElementById("search_box").value
	const price = document.getElementById("selected_price").value
	const rating = document.getElementById("selected_rating").value
	const sorting = document.getElementById("selected_sorting").value
	const venueType = document.getElementById("selected_venueType").value
	console.log(venueType)
	obj = {
		'searchPhrase': searchPhrase,
		'price': price,
		'sorting': sorting,
		'rating': rating,
		'venueType': venueType
	}
	json_body = JSON.stringify(obj);
	try {
		const response = await fetch('/venue/getfromquery', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: json_body
		});
		const statusCode = response.status;
		const data = await response.text();
		return JSON.parse(data)
	} catch (error) {
		// Handle errors
		console.log('error', error);
		return [];
	}

}

async function searchKeywords() {

	const searchPhrase = document.getElementById("search_box").value
	if (searchPhrase.length > 0) {
		obj = {
			'searchPhrase': searchPhrase,
			'price': '',
			'sorting': '',
			'rating': '',
			'venueType': ''
		}
		json_body = JSON.stringify(obj);
		try {
			const response = await fetch('/venue/getfromquery', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: json_body
			});
			const statusCode = response.status;
			const data = await response.text();
			return JSON.parse(data)
		} catch (error) {
			// Handle errors
			console.log('error', error);
			return [];
		}
	}
	else {
		return [];
	}
}

function selectVenueType(venue_type) {

	document.getElementById('selected_venueType').value = venue_type;
	fillSearchResults();
}

function fillSearchResults() {

	fetch('./ui_files/view_search_results.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			// Handle the response data
			searchPhrase().then(result => {
				var template = Handlebars.compile(data);
				console.log(result)
				const obj = { venues: result };
				console.log(obj['venues']);
				const htmldata = template(obj);
				const page_body = document.getElementById("search_results_section");
				page_body.innerHTML = htmldata;
			});
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});
}

Handlebars.registerHelper("printItems", function (rating) {
	var html = "";
	for (var i = 0; i < Math.floor(rating); i++) {
		html += '<span class="fa fa-star checked"></span>'
	}
	while (i < rating) {
		html += '<span class="fa fa-star-half checked"></span>'
		i++;
	}
	while (i < 5) {
		html += '<span class="fa fa-star"></span>'
		i++;
	}
	return html;
});

async function getVenue(venue_id) {
	const response = await fetch('/venue/getfromid?venue_id=' + venue_id, {
		method: 'GET'
	});
	const statusCode = response.status;
	const data = await response.text();
	return JSON.parse(data);
}

async function getReviews(venue_id) {
	const response = await fetch('/getreviews?venue_id=' + venue_id, {
		method: 'GET'
	});
	const statusCode = response.status;
	const data = await response.text();
	return JSON.parse(data);
}

async function getComments(review_id) {

	const token = localStorage.getItem('auth-token');
	const headers = new Headers();
	if (token != null) {
		headers.append('Content-Type', 'application/json');
		headers.append('Authorization', 'Bearer ' + token);
	}

	const response = await fetch('/getcomments?review_id=' + review_id, {
		method: 'GET',
		headers: headers
	});
	const statusCode = response.status;
	const data = await response.text();
	return JSON.parse(data);
}

function loadComments(review_id) {

	fetch('./ui_files/comments.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			const page_body = document.getElementById("review-section");
			// const template = Handlebars.compile(data);

			getComments(review_id).then(result => {
				console.log(result);
				if (result.isUserLiked == 1) {
					result['upvote_class'] = 'fas';
					result['downvote_class'] = 'far';
				}
				else if (result.isUserLiked == 2) {
					result['upvote_class'] = 'far';
					result['downvote_class'] = 'fas';
				}
				else {
					result['upvote_class'] = 'far';
					result['downvote_class'] = 'far';
				}
				var template = Handlebars.compile(data);
				const htmldata = template(result);
				page_body.innerHTML = htmldata;
			})
				.catch(error => {
					// Handle errors
					console.log('error', error);
				});
		});
}

function userRating(star) {
	for (var i = 1; i <= star; i++) {
		element = document.getElementById("user_star" + i)
		element.classList.remove('unchecked');
		element.classList.add('checked');
	}
	while (i <= 5) {
		element = document.getElementById("user_star" + i)
		element.classList.remove('checked');
		element.classList.add('unchecked');
		i++;
	}
	document.getElementById("selected_user_rating").value = star;
}

function likeReview(reviewId, liked, dislike) {

	obj = {
		'reviewId': reviewId,
		'liked': liked,
		'dislike': dislike
	}

	json_body = JSON.stringify(obj);

	const token = localStorage.getItem('auth-token');

	if (token == null) {
		alert('Please login to Like/Dislike!!');
		return false;
	}

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	headers.append('Content-Type', 'application/json');

	try {
		fetch('/createreviewlike', {
			method: 'POST',
			headers: headers,
			body: json_body
		})
			.then(response => response.text())
			.then(data => {
				loadComments(reviewId);

			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});

	} catch (error) {
		// Handle errors
		console.log('error', error);
		return [];
	}

}

function loadReviews(venueId) {

	fetch('./ui_files/review_block.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			const page_body = document.getElementById("review-section");
			// const template = Handlebars.compile(data);

			getReviews(venueId).then(result => {
				var template = Handlebars.compile(data);
				const htmldata = template({ 'reviews': result });
				page_body.innerHTML = htmldata;
			})
				.catch(error => {
					// Handle errors
					console.log('error', error);
				});
		});
}

function loadVenue(venue_id) {
	fetch('./ui_files/venue_page.html', {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			const page_body = document.getElementById("venueBlock");
			// const template = Handlebars.compile(data);

			getVenue(venue_id).then(result => {
				var template = Handlebars.compile(data);
				const htmldata = template(result);
				page_body.innerHTML = htmldata;
				showDivs(1);
				loadReviews(venue_id);
			})
				.catch(error => {
					// Handle errors
					console.log('error', error);
				});
		});
}

slideIndex = 1;

function plusDivs(n) {
	showDivs(slideIndex += n);
}

function currentDiv(n) {
	showDivs(slideIndex = n);
}

function showDivs(n) {
	var i;
	var x = document.getElementsByClassName("mySlides");
	var dots = document.getElementsByClassName("demo");
	if (n > x.length) { slideIndex = 1 }
	if (n < 1) { slideIndex = x.length }
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	for (i = 0; i < dots.length; i++) {
		dots[i].className = dots[i].className.replace(" w3-white", "");
	}
	x[slideIndex - 1].style.display = "block";
	dots[slideIndex - 1].className += " w3-white";
}

// loadVenue();

function submitReview(venueId) {

	const rating = document.getElementById("selected_user_rating").value
	const review = document.getElementById("review_text").value
	obj = {
		'venueId': venueId,
		'review': review,
		'rating': rating
	}
	json_body = JSON.stringify(obj);

	const token = localStorage.getItem('auth-token');

	if (token == null) {
		alert('Please login to review!!');
		closeVenueBlock();
		fillSearchResults();
		return false;
	}
	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	headers.append('Content-Type', 'application/json');

	try {
		fetch('/createreview', {
			method: 'POST',
			headers: headers,
			body: json_body
		})
			.then(response => response.text())
			.then(data => {
				alert("Thank you for you review!!");
				loadReviews(venueId);
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});

	} catch (error) {
		// Handle errors
		console.log('error', error);
		return [];
	}
}

function submitComment(reviewId) {

	const comment = document.getElementById("comment_text").value
	obj = {
		'reviewId': reviewId,
		'comment': comment
	}
	json_body = JSON.stringify(obj);

	const token = localStorage.getItem('auth-token');

	if (token == null) {
		alert('Please login to comment!!');
		closeVenueBlock();
		fillSearchResults();
		return false;
	}

	const headers = new Headers();
	headers.append('Authorization', 'Bearer ' + token);
	headers.append('Content-Type', 'application/json');

	try {
		fetch('/createcomment', {
			method: 'POST',
			headers: headers,
			body: json_body
		})
			.then(response => response.text())
			.then(data => {
				alert("Thank you for your comments!!!");
				loadComments(reviewId);
			})
			.catch(error => {
				// Handle errors
				console.log('error', error)
			});

	} catch (error) {
		// Handle errors
		console.log('error', error);
		return [];
	}
}

function closeVenueBlock() {
	document.getElementById('venueBlock').innerHTML = '';
}