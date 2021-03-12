document.addEventListener("DOMContentLoaded", function(){
    setTimeout(loadTable, 5000);
});

function loadTable() {
    $.ajax({
        type: 'GET',
        dataType: "json",
        url: '/orders/ajax-orders',
        success: function(response){
            $('#order-table tbody > tr').remove();
            $.each(response, function(index, order){
                $('#order-table tbody').append(
                    '<tr>' +
                    '  <td>' + order.orderId + '</td>' +
                    '  <td>' + order.customer.customerName + '</td>' +
                    '  <td>' + order.customer.customerPhone + '</td>' +
                    '  <td>' +
                    '     <a  th:href="@{/orders/accept/{id}(id=${order.orderId})}" th:if="${order.status == \'NEW\'}" class="btn btn-success">Accept</a> ' +
                    '     <a th:if="${order.status == \'ACCEPTED\'}" class="btn btn-secondary" th:href="@{/orders/accept/{id}(id=${order.orderId})}">Delivered</a>' +
                    '  </td>' +
                    '</tr>'
                );
            });

        },
        error: function(error){
            console.error('Error:', error);
        },
        complete: function() {
            // Schedule the next request when the current one's complete
            setTimeout(loadTable, 5000);
        }
    });
}