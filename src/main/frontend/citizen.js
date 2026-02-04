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

        document.querySelectorAll(".card-link").forEach(el => el.remove());

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

            const container = document.createElement("div");
            container.appendChild(productCard);
            container.className="containerProduct";
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
    
});


document.getElementById("filter").addEventListener("click", (event) => {
    event.preventDefault();
    const brand = document.getElementById('brand').value.trim() || null;
    const type = document.getElementById('type').value.trim() || null;
    const priceMin = document.getElementById('PriceMin').value.trim() || null;
    const priceMax = document.getElementById('PriceMax').value.trim() || null;
    const shop_afm = document.getElementById('shop_afm').value.trim() || null;
    console.log(brand);
    getAllProducts(brand, type, priceMin, priceMax, shop_afm);
});

document.addEventListener("DOMContentLoaded", () => {
    getAllProducts(null, null, null, null, null);
});


