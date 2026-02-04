async function getAllProducts(brandValue, typeValue, minVal, maxVal, afmValue) {
    try {
        const params = new URLSearchParams();
    
        if (brandValue) params.append('brand', brandValue);
        if (typeValue) params.append('type', typeValue);
        if (minVal) params.append('priceMin', minVal);
        if (maxVal) params.append('priceMax', maxVal);
        if (afmValue) params.append('shop_afm', afmValue);

        const response = await fetch(`http://localhost:8080/citizens/getProductsByFilters?${params.toString()}`);
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message);
        }

        const result = await response.json();

        let container = document.querySelector(".containerProduct");
        if (!container) {
            container = document.createElement("div");
            container.className = "containerProduct";
            const form = document.querySelector("#loginForm");
            form.insertAdjacentElement("afterend", container);
        }

        container.innerHTML = "";

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
            header.textContent = "Product";

            const brand = document.createElement("p");
            brand.textContent = element.brand;

            const type = document.createElement("p");
            type.textContent = element.type;

            const description = document.createElement("p");
            description.textContent = element.description;

            const price = document.createElement("p");
            price.textContent = element.price;

            const quantity = document.createElement("p");
            quantity.textContent = element.quantity;

            const productToCart = document.createElement("div");
            productToCart.className = "product-to-cart";

            const button = document.createElement("button");
            button.textContent = "Add to Cart";
            button.className = "add-to-cart-btn"; // ← ΤΟ ΣΗΜΑΝΤΙΚΟ
            productToCart.appendChild(button);

            productCard.append(header, brand, type, description, price, quantity, productToCart);
            navigator.appendChild(productCard);
            container.appendChild(navigator);
        });

    } catch (error) {
        console.error(error.message);
    }
}

document.querySelector(".go-to-cart").addEventListener("click", () => {
    window.location.href = "cart.html";
});

document.getElementById("filter").addEventListener("click", (event) => {
    event.preventDefault();
    const brand = document.getElementById('brand').value.trim() || null;
    const type = document.getElementById('type').value.trim() || null;
    const priceMin = document.getElementById('PriceMin').value.trim() || null;
    const priceMax = document.getElementById('PriceMax').value.trim() || null;
    const shop_afm = document.getElementById('shop_afm').value.trim() || null;

    getAllProducts(brand, type, priceMin, priceMax, shop_afm);
});

document.addEventListener("DOMContentLoaded", () => {
    getAllProducts(null, null, null, null, null);
});
