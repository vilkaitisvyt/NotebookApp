function deleteEntry(id) {
  console.log("deleting entry " + id);
  let token = localStorage.getItem("currentUser");
  let requestData = `id=${id}`;

  let request = new XMLHttpRequest();

  request.open("delete", "/entryDelete");
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
}
