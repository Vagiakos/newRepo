const productId = localStorage.getItem("brand");
const addMsg = document.getElementById("addMessage");
ProductDetails(productId);
async function ProductDetails(productId) {
    try {
        const response = await fetch("http://localhost:8080/products/getProduct?brand=" + productId);
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message);
        }
        const product = await response.json();
        const productCard = document.createElement("div");
        productCard.className = "product-Card";
        const brand = document.createElement("p");
        brand.innerHTML = product.brand;
        const type = document.createElement("p");
        type.innerHTML = product.type;
        const description = document.createElement("p");
        description.innerHTML = product.description;
        const price = document.createElement("p");
        price.innerHTML = product.price;
        let quantity = 1;
        const quantityCounter = document.createElement("div");
        quantityCounter.className = "quantity-Counter";

        const minButton = document.createElement("button");
        minButton.textContent = "−";

        const quantityText = document.createElement("span");
        quantityText.innerHTML = quantity;

        const plusButton = document.createElement("button");
        plusButton.textContent = "+";

        minButton.addEventListener("click", () => {
            if (quantity > 1) {
                quantity--;
                quantityText.textContent = quantity;
            }
        });

        plusButton.addEventListener("click", () => {
            quantity++;
            quantityText.textContent = quantity;
        });

        quantityCounter.append(minButton, quantityText, plusButton);

        productCard.append(
            brand,
            type,
            description,
            price,
            quantityCounter
        );
        document.getElementById("product-container").appendChild(productCard);

        document.querySelector(".add-to-cart").addEventListener("click", async () => {
            const cartId = localStorage.getItem("cartId");
            try {
                const response = await fetch("http://localhost:8080/products/addProductToCart?cartId=" + cartId + "&brand=" + productId + "&quantity=" + quantity, { method: "POST" });

                if (!response.ok) {
                    const errorData = await response.json();
                    throw new Error(errorData.message);
                }
                addMsg.textContent = "Product added to cart";
            } catch (error) {
                addMsg.textContent = error.message;
            }
        });

    } catch (error) {
        console.error(error.message);
    }

}
