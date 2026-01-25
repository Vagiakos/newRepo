const afm = localStorage.getItem("afm"); // get shop afm from localStorage (saved at login)
const container = document.getElementById("productsContainer"); 
const addForm = document.getElementById("addProductForm"); 
const addMsg = document.getElementById("addMessage"); 

// load all products for this shop
async function loadProducts() {
    try {
        const res = await fetch(`http://localhost:8080/shops/getProductsFromShop?afm=${afm}`);
        if (!res.ok) throw new Error(await res.text());

        const products = await res.json(); // json to js object/array
        console.log("Products from backend:", products); // debug
        container.innerHTML = ""; // clear container

        products.forEach(p => { //for every product cart 
            const card = document.createElement("div"); //new div
            card.className = "product-Card";
            card.innerHTML = `
                <h3>${p.brand}</h3>
                <p>Type: ${p.type}</p>
                <p>Description: ${p.description}</p>
                <p>Price: ${p.price} €</p>
                <label>Quantity:</label>
                <input type="number" min="0" value="${p.quantity}" class="qty-input">
                <button class="update-btn">Update</button>
                <button class="delete-btn">Delete</button>
            `;
            card.querySelector(".update-btn").onclick = () => updateQuantity(p.brand, card.querySelector(".qty-input").value);
            card.querySelector(".delete-btn").onclick = () => deleteProduct(p.brand);

            container.appendChild(card);
        });
    } catch (e) {
        console.error("load products error:", e);
        alert(e.message);
    }
}

// add product
addForm.addEventListener("submit", async e => {
    e.preventDefault(); 
    const product = { //object product with all input fields
        brand: document.getElementById("brand").value,
        type: document.getElementById("type").value,
        description: document.getElementById("description").value,
        price: document.getElementById("price").value,
        quantity: document.getElementById("quantity").value,
        shop: { afm } //shop afm
    };

    try {
        const res = await fetch("http://localhost:8080/shops/addProduct", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(product)
        });
        if (!res.ok) throw new Error(await res.text());

        addMsg.textContent = "Product added successfully!";
        addForm.reset();
        loadProducts();
    } catch (e) {
        addMsg.textContent = e.message;
    }
});

// update quantity
async function updateQuantity(brand, quantity) {
    if (quantity < 0 || quantity === "") return alert("Invalid quantity");

    try {
        const res = await fetch(`http://localhost:8080/products/updateQuantity?brand=${brand}&quantity=${quantity}`, { method: "PUT" });
        if (!res.ok) throw new Error(await res.text());
        loadProducts();
    } catch (e) {
        alert(e.message);
    }
}

// delete product
async function deleteProduct(brand) {
    if (!confirm("Are you sure?")) return; 

    try {
        const res = await fetch(`http://localhost:8080/shops/deleteProduct?brand=${brand}`, { method: "DELETE" });
        if (!res.ok) throw new Error(await res.text());
        loadProducts();
    } catch (e) {
        alert(e.message);
    }
}

loadProducts();