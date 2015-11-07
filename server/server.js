var Express = require('express');
var request = require('request');
var bodyParser = require('body-parser');

var app = Express();

app.use(Express.static('public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var port = process.env.PORT || 8080;

app.post('/action', function(req, res) {
	var user_token = req.body.userToken;

	request({
		method: 'POST',
		url: 'https://inapp.playground.klarna.com/api/v1/users/#{user_token}/orders',
		json: true,
		body: {
			reference: req.body.article_id,
			user_token: user_token,
			name: 'LUUUUKE',
			order_amount: 99,
			tax_amount: 9,
			currency: 'EUR',
			origin_proof: req.body.origin_proof
		}
	}, function(err, response, body) {
		if (err) {
			res.json({
				'result': 'error',
				'err': err
			});
		}
		else {
			res.json({
				'result': 'success',
				'err': null
			});
		}
	});
});

app.get('/test', function(req, res) {
	res.send('BUSKR!');
});

var server = app.listen(port, function() {
	var host = server.address().address;
	var port = server.address().port;

	console.log('app at http://%s:%s', host, port);
});