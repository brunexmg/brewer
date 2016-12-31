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

Brewer.MaskDate = (function () {
	function MaskDate() {
		this.inputData = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputData.mask('00/00/0000');
		this.inputData.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
}());

Brewer.MaskHour = (function () {
	function MaskHour() {
		this.inputHour = $('.js-hour');
	}
	
	MaskHour.prototype.enable = function() {
		this.inputHour.mask('00:00');
	}
	
	return MaskHour;
}());

Brewer.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
}());

numeral.language('pt-br');

Brewer.formatarMoeda = function(valor) {
	return numeral(valor).format('0,0.00');
}

Brewer.recuperarValor = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskCep = new Brewer.MaskCep();
	maskCep.enable();
	
	var maskDate = new Brewer.MaskDate();
	maskDate.enable();
	
	var maskHour = new Brewer.MaskHour();
	maskHour.enable();
	
	var security = new Brewer.Security();
	security.enable();
});