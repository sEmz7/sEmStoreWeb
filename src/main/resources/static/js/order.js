document.getElementById("order-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const orderData = {
        productLink: document.getElementById("product-link").value,
        size: document.getElementById("size").value,
        color: document.getElementById("color").value
    };

    fetch("/api/order", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(orderData)
    })
    .then(response => response.json())
    .then(data => {
        alert("Заказ успешно отправлен!");
    })
    .catch(error => {
        console.error("Ошибка при отправке заказа:", error);
    });
});
