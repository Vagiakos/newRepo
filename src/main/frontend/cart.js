const cartID = localStorage.getItem("cartId");
const messageSuccess = document.getElementById("message");
getProductsFromCart(cartID);

async function getProductsFromCart(cartID) {
    try {
        const response = await fetch("http://localhost:8080/cart/getCartProducts?cartId=" + cartID);
        const result = await response.json();
        if (!response.ok) {
            throw new Error(result.message);
        }


        if (result.length === 0) {
            const h1 = document.querySelector("h1");

            if (!document.getElementById("empty-cart")) {
                h1.insertAdjacentHTML(
                    "afterend",
                    "<p id='empty-cart'>Empty cart!</p>"
                );
            }
            return;
        }
        result.forEach(element => {
            const productCard = document.createElement("div");
            productCard.className = "product-to-cart";
            const header = document.createElement("h2");
            header.innerHTML = "Product";
            const brand = document.createElement("p");
            brand.innerHTML = element.brand;
            const price = document.createElement("p");
            price.innerHTML = element.price;
            let quantity = element.quantity;;
            const quantityCounter = document.createElement("div");
            quantityCounter.className = "quantity-Counter";

            const minButton = document.createElement("button");
            minButton.textContent = "−";

            const quantityText = document.createElement("span");
            quantityText.innerHTML = quantity;

            const plusButton = document.createElement("button");
            plusButton.textContent = "+";

            quantityCounter.append(minButton, quantityText, plusButton);
            minButton.addEventListener("click", async () => {
                if (quantity <= 0) return;

                try {
                    const response = await fetch("http://localhost:8080/cart/removeProductFromCart?cartId=" + cartID + "&brand=" + element.brand + "&quantity=" + 1, { method: "DELETE" });

                    if (!response.ok) {
                        const errorData = await response.json();
                        throw new Error(errorData.message);
                    }



                    quantity--;
                    quantityText.textContent = quantity;
                    if (quantity == 0) {
                        productCard.remove();
                    }

                } catch (error) {
                    console.log(error.message);
                }


            });

            plusButton.addEventListener("click", async () => {

                try {
                    const response = await fetch("http://localhost:8080/cart/addProductToCart?cartId=" + cartID + "&brand=" + element.brand + "&quantity=" + 1, { method: "POST" });

                    if (!response.ok) {
                        const errorData = await response.json();
                        throw new Error(errorData.message);
                    }



                    quantity++;
                    quantityText.textContent = quantity;
                } catch (error) {
                    console.log(error.message);
                }

            });

            const deleteButton = document.createElement("button");
            deleteButton.innerHTML = "Delete";
            deleteButton.className = "delete-cart-item"
            deleteButton.addEventListener("click", async () => {

                try {
                    const response = await fetch("http://localhost:8080/cart/removeProductFromCart?cartId=" + cartID + "&brand=" + element.brand + "&quantity=" + quantity, { method: "DELETE" });

                    if (!response.ok) {
                        const errorData = await response.json();
                        throw new Error(errorData.message);
                    }

                    messageSuccess.textContent = "Product Removed Successfully";

                    productCard.remove();
                } catch (error) {
                    console.log(error.message);
                }



            });
            productCard.appendChild(header);
            productCard.appendChild(brand);
            productCard.appendChild(price);
            productCard.appendChild(quantityCounter);
            productCard.appendChild(deleteButton);
            const h1 = document.querySelector("h1");
            h1.insertAdjacentElement("afterend", productCard);


        });
    } catch (error) {
        message.textContent = error.message;
    }

}

const buybutton = document.querySelector(".buy-products");
buybutton.addEventListener("click", async () => {
    try {
        const response = await fetch("http://localhost:8080/cart/buyProductsFromCart?cartId=" + cartID, { method: "POST" });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message);
        }

        console.log(response);
        messageSuccess.textContent = "Products purchased Successfully";

        document.querySelectorAll(".product-to-cart").forEach(el => el.remove());



    } catch (error) {
        console.log(error.message);
    }
});