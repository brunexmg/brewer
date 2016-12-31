var Brewer = Brewer || {};

Brewer.DialogoExcluir = (function() {
	function DialogoExcluir() {
		this.exclusaoBtn = $('.js-exclusao-btn');
		this.totalElementos = $('input[name=registrosPagina]').val();
	    this.paginaAtual = $('input[name=atual]');
	    this.ultimaPagina = $('input[name=ultima]').val();
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
		
		if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto', 'Excluido com sucesso!', 'success');
		}
	}
	
	function onExcluirClicado(evento) {
		evento.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		
		swal({
			title: 'Tem certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			type: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#DD6855',
			confirmButtonText: 'Sim, exclua agora!',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, url));	
	}
	
	function onExcluirConfirmado(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso() {
		var urlPaginacao = null;
		var urlFinal = null;
	    var urlAtual = window.location.href;
	    var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
			
	    if (this.ultimaPagina = 'true' && this.totalElementos == '1') {
	    	var paginaAnterior = this.paginaAtual.val() - 1;
	    	urlPaginacao = 'cervejas?page=' + paginaAnterior.toString();
	    	urlFinal = urlPaginacao.indexOf('excluido') > -1 ? urlPaginacao : urlPaginacao + separador + 'excluido';
	    } else {
	    	urlFinal = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
	    }
			
	    window.location = urlFinal;
	}
	
	/*function onExcluidoSucesso() {
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}*/
	
	function onErroExcluir(e) {
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoExcluir;
}());



$(function() {
	var dialogoExcluir = new Brewer.DialogoExcluir();
	dialogoExcluir.iniciar();
});