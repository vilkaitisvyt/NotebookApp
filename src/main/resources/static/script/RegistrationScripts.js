const getRegistrationPage = () => {	
    console.log("geting registration page");

    let redir = new XMLHttpRequest();

    redir.open('GET', '/registrationPage');
    redir.onload = () => {
        document.getElementById('holder').innerHTML = redir.responseText;
    };
    redir.send();
};

const registrationForm = {
    username:null,
    password:null,
    message:null
};

const submitRegistrationData = () => {
    let request = new XMLHttpRequest();
    registrationForm.username = document.getElementById('username'),
    registrationForm.password = document.getElementById('password'),
    registrationForm.message = document.getElementById('form-message')
    let requestData = `username=${registrationForm.username.value}&password=${registrationForm.password.value}`;

    request.open('post', '/register');
    request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    request.onload = () => {
        let responseObject = null;

        try {
            responseObject = JSON.parse(request.responseText);
        } catch (e) {
            console.error('Could not parse JSON');
        }

        if(responseObject) {
            handleResponse(responseObject);
        }
    };
    
    if (registrationForm.username.value != "" && registrationForm.password.value != "") {
        request.send(requestData);
    }
    
};

function handleResponse(responseObject) {
    if(responseObject.message == "") {
        console.log("Registration successful");
        getLoginPage();
        
    } else {
        while(registrationForm.message.firstChild) {
            registrationForm.message.removeChild(registrationForm.message.firstChild);
        }
        
        let message = responseObject.message;
        const li = document.createElement('li');
        li.textContent = message;
        registrationForm.message.appendChild(li);
        registrationForm.message.style.display = "block";
    }
};