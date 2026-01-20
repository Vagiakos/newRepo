const select_type = document.getElementById("type");
let type = select_type.value;
if (type == "Shop") {
    document.getElementById("user_label_1").innerHTML = "Brand Shop: ";
    document.getElementById("user_input_1").placeholder = "Brand Shop";
    document.getElementById("user_label_2").innerHTML = "Owner: ";
    document.getElementById("user_input_2").placeholder = "Owner";
} else if (type == "Citizen") {
    document.getElementById("user_label_1").innerHTML = "Name: ";
    document.getElementById("user_input_1").placeholder = "Name";
    document.getElementById("user_label_2").innerHTML = "Surname: ";
    document.getElementById("user_input_2").placeholder = "Surname";
}

select_type.addEventListener("change", () => {
    type = select_type.value;
    if (type == "Shop") {
        document.getElementById("user_label_1").innerHTML = "Brand Shop: ";
        document.getElementById("user_input_1").placeholder = "Brand Shop";
        document.getElementById("user_label_2").innerHTML = "Owner: ";
        document.getElementById("user_input_2").placeholder = "Owner";
    } else if (type == "Citizen") {
        document.getElementById("user_label_1").innerHTML = "Name: ";
        document.getElementById("user_input_1").placeholder = "Name";
        document.getElementById("user_label_2").innerHTML = "Surname: ";
        document.getElementById("user_input_2").placeholder = "Surname";
    }
});

//fishing elements
const RegisterForm = document.getElementById("RegisterForm");
const message = document.getElementById("message");

// submit event in form ('Enter' or 'click')
RegisterForm.addEventListener("submit", function (event) {
    console.log("okeey");
    event.preventDefault(); // avoid default refresh

    // user input 
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const afm = document.getElementById("afm").value;
    const username = document.getElementById("username").value;
    const user_input_1 = document.getElementById("user_input_1").value;
    const user_input_2 = document.getElementById("user_input_2").value;



    // object for backend (dto)
    let RegisterRequest;
    if (type == "Shop") {
        RegisterRequest = {
            username: username,
            afm: afm,
            email: email,
            password: password,
            typeOfUser: type,
            brandShop: user_input_1,
            owner: user_input_2


        };
    } else if (type == "Citizen") {
        RegisterRequest = {
            username: username,
            afm: afm,
            email: email,
            password: password,
            typeOfUser: type,
            name: user_input_1,
            surname: user_input_2


        };
    }


    fetch("http://localhost:8080/user/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json" //send json to backend
        },
        body: JSON.stringify(RegisterRequest) //read it as json (Οbject loginRequest -> String)
    })
        .then(async response => {
            // if backend returns error status
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message); 
            }
            return;
        })
        .then(() => {
            // success
            if (type == "Shop") {
                message.textContent = "Shop registered Successfully!";
            } else if (type == "Citizen") {
                message.textContent = "Citizen registered Successfully!";

            }

            // // recognize role from the backend message
            // if (data.includes("Shop")) {
            //     localStorage.setItem("role", "Shop");
            // } else {
            //     localStorage.setItem("role", "Citizen");
            // }

            // localStorage.setItem("email", email);

            //delay 3 sec before redirect
            setTimeout(() => {
                // navigate to main page
                window.location.href = "login.html";
            }, 3000);

        })
        .catch(error => {
            // error from backend
            message.textContent = error.message;
        });
});

