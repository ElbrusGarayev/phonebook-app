import React from 'react';

const NavBar = () => (
    <nav>
        <div className="nav-wrapper grey darken-4">
        <a className="brand-logo center">Phonebook app from host: {window?.location?.hostname}
        </a>
        </div>
    </nav>
);

export default NavBar;