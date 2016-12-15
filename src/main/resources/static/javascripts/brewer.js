var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		this.plain.maskMoney({ precision: 0, thousands: '.' });
	}
	
	return MaskMoney;
	
}());

Brewer.MaskPhoneNumber = (function() {
	function MaskPhoneNumber() {
		this.imputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		},
		options = {
			onKeyPress: function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);
			}		
		};
		this.imputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MaskPhoneNumber;
}());

Brewer.MaskCep = (function() {
	function MaskCep() {
		this.imputCep = $('#cep');
	}
	
	MaskCep.prototype.enable = function() {
		var maskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 8 ? null : '00.000-000';
		},
		options = {
			onKeyPress: function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);
			}		
		};
		this.imputCep.mask(maskBehavior, options);
	}
	
	return MaskCep;
}());

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskCep = new Brewer.MaskCep();
	maskCep.enable();
});