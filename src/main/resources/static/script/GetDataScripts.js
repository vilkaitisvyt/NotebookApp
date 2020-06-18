const getData = () => {
  console.log("geting data");
  let token = localStorage.getItem("currentUser");

  let redir = new XMLHttpRequest();

  redir.open("GET", "/entries");
  redir.setRequestHeader("Authorization", "Bearer " + token);
  redir.onload = () => {
    if (redir.status === 403) {
      logout();
    }
    document.getElementById("holder").innerHTML = redir.responseText;
  };

  if (token) {
    redir.send();
  } else {
    getLoginPage();
  }
};
