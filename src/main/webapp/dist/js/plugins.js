/*提示层*/
$(function () {
    // 鼠标指向提示层
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover();
    //分页功能
    var options = {
        currentPage:2,
        totalPages:5,
        numberOfPages:5
    }
    $('#page').bootstrapPaginator(options);

});