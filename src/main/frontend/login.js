//fishing elements
const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");

// submit event in form ('Enter' or 'click')
loginForm.addEventListener("submit", function (event) {
    event.preventDefault(); // avoid default refresh

    // user input 
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // object for backend (dto)
    const loginRequest = {
        email: email,
        password: password
    };

    fetch("http://localhost:8080/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json" //send json to backend
        },
        body: JSON.stringify(loginRequest) //read it as json (Οbject loginRequest -> String)
    })
    .then(async response => {
        // if backend returns error status
        if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message); 
            }
            return response.text();;
    })
    .then(data => {
        // success
        message.textContent = "Successful login";
        
        // recognize role from the backend message
        if (data.includes("Shop")) {
            localStorage.setItem("role", "Shop");
        } else {
            localStorage.setItem("role", "Citizen");
        }

        localStorage.setItem("email", email);

        //delay 3 sec before redirect
        setTimeout(() => {
            // navigate to main page
            window.location.href = "main.html";
        }, 3000); 

    })
    .catch(error => {
        // error from backend
        message.textContent = error.message;
    });
});