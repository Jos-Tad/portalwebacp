  const inputbtn = document.getElementById("multVision");
        const customTxt = document.getElementById("labelmulVision");

        inputbtn.addEventListener("change", function () {
            if (inputbtn.value) {
                customTxt.innerHTML = inputbtn.value.match(
                    /[\/\\]([\w\d\s\.\-\(\)]+)$/
                )[1];
            } else {
                customTxt.innerHTML = "Ningún archivo seleccionado";
            }
        });