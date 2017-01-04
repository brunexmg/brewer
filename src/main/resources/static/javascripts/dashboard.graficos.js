var Brewer = Brewer || {};

Brewer.GraficoVendaPorMes = (function() {
	function GraficoVendaPorMes() {
		this.ctx = $('#graficoVendasPorMes')[0].getContext('2d');
		this.semDados = $('.js-nao-ha-dados');
	}
	
	GraficoVendaPorMes.prototype.iniciar = function() {
		$.ajax({
			url: 'vendas/totalPorMes',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(vendaMes) {
		var mesesAbreviados = ['', 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
		var meses = [];
		var valores = [];
		vendaMes.forEach(function(obj) {
			meses.unshift(obj.mes.split("/")[0] + '/' + mesesAbreviados[parseInt(obj.mes.split("/")[1])]);
			valores.unshift(obj.total);
		});	
		var graficoVendasPorMes = new Chart(this.ctx, {
			type: 'line',
			data: {
				labels: meses,
				datasets: [{
					label: 'Vendas por mês',
					backgroundColor: 'rgba(26,179,148,0.5)',
					pointBorderColor: 'rgba(26,179,148,1)',
					pointBackgroundColor: '#fff',
					data: valores
				}]
			},
		});
	}
	
	return GraficoVendaPorMes;
}());

Brewer.GraficoVendaPorOrigem = (function() {
	function GraficoVendaPorOrigem() {
		this.ctx = $('#graficoVendaPorOrigem')[0].getContext('2d');
		this.semDados = $('.js-nao-ha-dados');
	}
	
	GraficoVendaPorOrigem.prototype.iniciar = function() {
		$.ajax({
			url: 'vendas/totalPorOrigem',
			method: 'GET',
			success: onDadosRecebidos.bind(this)
		});
	}
	
	function onDadosRecebidos(vendaOrigem) {
		var mesesAbreviados = ['', 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
		var meses = [];
		var cervejasNacional = [];
		var cervejasInternacional = [];
		vendaOrigem.forEach(function(obj) {
			meses.unshift(obj.mes.split("/")[0] + '/' + mesesAbreviados[parseInt(obj.mes.split("/")[1])]);
			cervejasNacional.unshift(obj.totalNacional);
			cervejasInternacional.unshift(obj.totalInternacional);
		});
		var graficoVendasPorOrigem = new Chart(this.ctx, {
			type: 'bar',
			data: {
				labels: meses,
				datasets: [{
					label: 'Nacional',
					backgroundColor: 'rgba(220,220,220,0.5)',
					data: cervejasNacional
				},
				{
					label: 'Internacional',
					backgroundColor: 'rgba(26,179,148,0.5)',
					data: cervejasInternacional
				}]
			},
		});
	}
	
	return GraficoVendaPorOrigem;
}());

$(function() {
	var graficoVendaPorMes = new Brewer.GraficoVendaPorMes();
	graficoVendaPorMes.iniciar();
	
	var graficoVendaPorOrigem = new Brewer.GraficoVendaPorOrigem();
	graficoVendaPorOrigem.iniciar();
});