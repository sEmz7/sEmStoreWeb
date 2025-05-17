document.getElementById("order-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const orderData = {
        link: document.getElementById("link").value,
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
            if (data.status === "success") {
                alert("Заказ успешно отправлен!");
                window.location.href = "/profile";
            } else {
                alert("Что-то пошло не так. Попробуйте снова.");
            }
        })
        .catch(error => {
            console.error("Ошибка при отправке заказа:", error);
            alert("Ошибка при отправке заказа. Попробуйте снова.");
        });
});
