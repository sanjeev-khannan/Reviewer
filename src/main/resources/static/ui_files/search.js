
fillSearchResults();

async function searchPhrase(){
    
    const searchPhrase = document.getElementById("search_box").value
	const price = document.getElementById("selected_price").value
	const rating = document.getElementById("selected_rating").value
	const sorting = document.getElementById("selected_sorting").value
	obj = {
		'searchPhrase': searchPhrase,
		'price': price,
		'sorting': sorting,
		'rating': rating
	}
	json_body = JSON.stringify(obj);
	try{
		const response = await fetch('/venue', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: json_body
		});
		const statusCode = response.status;
		const data = await response.text();
		return JSON.parse(data)
	} catch(error) {
			// Handle errors
			console.log('error', error);
			return [];
		}

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
				const htmldata = template({ venues : result });
				const page_body = document.getElementById("search_results_section");
				page_body.innerHTML = htmldata;
			});
		})
		.catch(error => {
			// Handle errors
			console.log('error', error)
		});
}

Handlebars.registerHelper("printItems", function(rating) {
	var html = "";
	for(var i=0; i<Math.floor(rating); i++){
		html += '<span class="fa fa-star checked"></span>'
	}
	while(i<rating){
		html += '<span class="fa fa-star-half checked"></span>'
		i++;
	}
	while(i<5){
		html += '<span class="fa fa-star"></span>'
		i++;
	}
	return html;
  });
