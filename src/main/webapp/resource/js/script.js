const mobileNav = document.querySelector(".hamburger");
const navbar = document.querySelector(".menubar");

const toggleNav = () => {
    navbar.classList.toggle("active");
    mobileNav.classList.toggle("hamburger-active");
};
mobileNav.addEventListener("click", () => toggleNav())

document.addEventListener('DOMContentLoaded', function() {
    const navbar = document.querySelector('.navbar');
    const scrollThreshold = 20;
    
    const slideElement = document.querySelector('.sliding-element'); 
    const maxScrollForSlide = 700;
    const maxSlideDistance = 300;
    const mobileBreakpoint = 790;
    
    function toggleNavbarBlur() {
        const scrolledDistance = window.scrollY || window.pageYOffset; 

        if (scrolledDistance > scrollThreshold) {
            if (!navbar.classList.contains('scrolled')) {
                navbar.classList.add('scrolled');
            }
        } else {
            if (navbar.classList.contains('scrolled')) {
                navbar.classList.remove('scrolled');
            }
        }
    }
    
    function moveSlidingElement() {
        if (window.innerWidth <= mobileBreakpoint) {
            slideElements.forEach(el => {
                el.style.transform = `translate(-50%, -50%)`;
            });
            return;
        }

        const scrolledDistance = window.scrollY || window.pageYOffset;
        let slideAmount = 0;

        if (scrolledDistance <= maxScrollForSlide) {
            const scrollRatio = scrolledDistance / maxScrollForSlide;
            slideAmount = maxSlideDistance - (maxSlideDistance * scrollRatio);
            
        } else {
            slideAmount = 0; 
        }

        slideElement.style.transform = `translate(calc(-55% + ${slideAmount * 0.5}px), calc(-35% - ${slideAmount * 0.6}px)) scale(${1 - slideAmount * 0.0068})`;
    }

    window.addEventListener('scroll', toggleNavbarBlur);
    window.addEventListener('scroll', moveSlidingElement);
    toggleNavbarBlur();
    moveSlidingElement();
});
