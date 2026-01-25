async function getAllProducts() {
    try {
        const response = await fetch("http://localhost:8080/citizens/getAllProducts");
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message);
        }

        const result = await response.json();
        result.forEach(element => {
            const navigator = document.createElement("a");
            navigator.href = "product.html";
            navigator.className = "card-link";
            navigator.addEventListener("click", () => {
                localStorage.setItem("brand", element.brand);
            });
            const productCard = document.createElement("div");
            productCard.className = "product-Card";
            const header = document.createElement("h2");
            header.innerHTML = "Product";
            const brand = document.createElement("p");
            brand.innerHTML = element.brand;
            const type = document.createElement("p");
            type.innerHTML = element.type;
            const description = document.createElement("p");
            description.innerHTML = element.description;
            const price = document.createElement("p");
            price.innerHTML = element.price;
            const quantity = document.createElement("p");
            quantity.innerHTML = element.quantity;
            productCard.appendChild(header);
            productCard.appendChild(brand);
            productCard.appendChild(type);
            productCard.appendChild(description);
            productCard.appendChild(price);
            productCard.appendChild(quantity);
            navigator.appendChild(productCard);
            const h1 = document.querySelector("h1");
            h1.insertAdjacentElement("afterend", navigator);
        });


    } catch (error) {
        console.error(error.message);
    }
}

document.querySelector(".go-to-cart").addEventListener("click", () => {
    window.location.href = "cart.html";
    
})



getAllProducts();