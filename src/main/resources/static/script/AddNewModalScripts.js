const modalForm = {
  newEntry: null,
};

const addNewEntry = () => {
  console.log("adding entry");
  modalForm.newEntry = document.getElementById("newEntry");
  let token = localStorage.getItem("currentUser");
  let requestData = `newEntry=${modalForm.newEntry.value}`;

  let request = new XMLHttpRequest();

  request.open("post", "/entryNew");
  request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  request.setRequestHeader("Authorization", "Bearer " + token);
  request.onload = () => {
    if (request.status === 403) {
      logout();
    }
    document.getElementById("holder").innerHTML = request.responseText;
  };

  if (token) {
    request.send(requestData);
  } else {
    getLoginPage();
  }
};
