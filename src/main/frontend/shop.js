const afm = localStorage.getItem("afm"); // get shop afm from localStorage (saved at login)
const container = document.getElementById("productsContainer");
const addForm = document.getElementById("addProductForm");
const addMsg = document.getElementById("addMessage");

// if afm is missing -> avoid broken requests (afm=null)
console.log("AFM from localStorage:", afm); // debug
if (!afm) {
    alert("AFM not found. Please login again.");
    window.location.href = "login.html"; // change if your login page has different name
}

// load all products for this shop
async function loadProducts() {
    try {
        const res = await fetch(`http://localhost:8080/shops/getProductsFromShop?afm=${encodeURIComponent(afm)}`);
        if (!res.ok) throw new Error(await res.text());

        const products = await res.json(); // json to js object/array
        console.log("Products from backend:", products); // debug

        container.innerHTML = ""; // clear container

        // if backend returns something unexpected
        if (!Array.isArray(products)) {
            throw new Error("Backend did not return a product list.");
        }

        products.forEach(p => { // for every product cart
            // safety check
            if (!p || !p.brand) return;

            const card = document.createElement("div"); // new div
            card.className = "product-Card";

            // create unique id for label/input connection
            const qtyId = `qty-${String(p.brand).replace(/\s+/g, "_")}`;

            card.innerHTML = `
                <h3>${p.brand}</h3>
                <p>Type: ${p.type ?? ""}</p>
                <p>Description: ${p.description ?? ""}</p>
                <p>Price: ${p.price ?? 0} €</p>

                <label for="${qtyId}">Quantity:</label>
                <input 
                    id="${qtyId}" 
                    name="quantity"
                    type="number" 
                    min="0" 
                    value="${p.quantity ?? 0}" 
                    class="qty-input"
                >

                <button class="update-btn" type="button">Update</button>
                <button class="delete-btn" type="button">Delete</button>
            `;

            card.querySelector(".update-btn").onclick = () =>
                updateQuantity(p.brand, card.querySelector(".qty-input").value);

            card.querySelector(".delete-btn").onclick = () =>
                deleteProduct(p.brand);

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

    const product = { // object product with all input fields
        brand: document.getElementById("brand").value,
        type: document.getElementById("type").value,
        description: document.getElementById("description").value,
        price: Number(document.getElementById("price").value),
        quantity: Number(document.getElementById("quantity").value),
        shop: { afm } // shop afm
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
    const qty = Number(quantity);

    if (!brand) return alert("Invalid product brand");
    if (Number.isNaN(qty) || qty < 0) return alert("Invalid quantity");

    try {
        const res = await fetch(
            `http://localhost:8080/products/updateQuantity?brand=${encodeURIComponent(brand)}&quantity=${qty}`,
            { method: "PUT" }
        );

        if (!res.ok) throw new Error(await res.text());
        loadProducts();
    } catch (e) {
        alert(e.message);
    }
}

// delete product
async function deleteProduct(brand) {
    if (!brand) return alert("Invalid product brand");
    if (!confirm("Are you sure?")) return;

    try {
        const res = await fetch(
            `http://localhost:8080/shops/deleteProduct?brand=${encodeURIComponent(brand)}`,
            { method: "DELETE" }
        );

        if (!res.ok) throw new Error(await res.text());
        loadProducts();
    } catch (e) {
        alert(e.message);
    }
}

loadProducts();
