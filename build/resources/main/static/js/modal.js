$(document).mouseup(function (e) {
    // 영역 바깥 클릭시 포스팅박스가 숨겨지는 기능
    if ($('#modal').has(e.target).length === 0) {
        $('#modal_wrap').hide();
    }
});


function posting() {
    let title = $('#modal-title').val();
    let content = $('#modal-content').val();
    let author = $("#modal-author").val();

    let data = {'title': title,'author': author,'content': content};

    $.ajax({
        type: "POST",
        url: "/board/post",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            alert("작성 완료!");
            window.location.reload();
        }
    })
}

function modal_show() {
    $('#modal').show();
}