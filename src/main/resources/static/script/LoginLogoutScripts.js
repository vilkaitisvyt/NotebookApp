const logout = () => {
  localStorage.removeItem("currentUser");
  getLoginPage();
};

const getLoginPage = () => {
  console.log("geting login page");

  let redir = new XMLHttpRequest();

  redir.open("GET", "/login");
  redir.onload = () => {
    document.getElementById("holder").innerHTML = redir.responseText;
  };
  redir.send();
};

const loginForm = {
  username: null,
  password: null,
  message: null,
};

const submitLoginData = () => {
  let request = new XMLHttpRequest();
  (loginForm.username = document.getElementById("username")),
    (loginForm.password = document.getElementById("password")),
    (loginForm.message = document.getElementById("form-message"));
  let requestData = `username=${loginForm.username.value}&password=${loginForm.password.value}`;

  request.open("post", "/authenticate");
  request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  request.onload = () => {
    let responseObject = null;

    try {
      responseObject = JSON.parse(request.responseText);
    } catch (e) {
      console.error("Could not parse JSON");
    }

    if (responseObject) {
      handleResp(responseObject);
    }
  };

  request.send(requestData);
};

function handleResp(responseObject) {
  if (responseObject.jwt != "") {
    localStorage.setItem("currentUser", responseObject.jwt);
    console.log("login successful");
    getData();
  } else {
    while (loginForm.message.firstChild) {
      loginForm.message.removeChild(loginForm.message.firstChild);
    }

    let message = responseObject.message;
    const li = document.createElement("li");
    li.textContent = message;
    loginForm.message.appendChild(li);
    loginForm.message.style.display = "block";
  }
}
