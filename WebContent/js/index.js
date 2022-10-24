const main = () => {
    const buttonSearch = document.querySelector('#btnSearch');
    buttonSearch.addEventListener('click', (event) => {
        event.preventDefault();
        let inputSearch = document.querySelector('#inputSearch1');
        if (inputSearch.classList.contains('d-none')) {
            inputSearch.classList.remove('d-none');
        } else {
            inputSearch.classList.add('d-none');
        }
    });
}
var myCarousel = document.querySelector('#mycarousel')
var carousel = new bootstrap.Carousel(myCarousel, {
    interval: 1500,
    wrap: true
})

// Llamada de funciones
main();
