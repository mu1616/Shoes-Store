//현재페이지의 페이지버튼 파란색으로 표시
function makePageBtn(currentPage){
	var btns = document.getElementsByClassName('pageBtn');
	for(var i=0; i<btns.length; i++) {
		if(btns[i].innerText == currentPage){
			btns[i].classList.remove('btn-outline-secondary');
			btns[i].classList.add('btn-outline-primary');
		}
	}
}
