'use client';
import React from 'react';
import { Nav } from './Navbar.styles';
import { PiHouseFill } from 'react-icons/pi';
import NavbarRoute from '@/components/Navbar/NavbarRoute';
import { useTheme } from 'styled-components';

const Navbar = () => {
    const { neutral5 } = useTheme();

    return (
        <Nav>
            <NavbarRoute icon={<PiHouseFill size={16} color={neutral5} />} href="/">
                Home
            </NavbarRoute>
        </Nav>
    );
};

export default Navbar;
