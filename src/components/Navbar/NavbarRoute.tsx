'use client';
import React, { ReactNode } from 'react';
import { NavRoute } from '@/components/Navbar/NavbarRoute.styles';

type Props = {
    icon: ReactNode;
    children: ReactNode;
    href: string;
};

const NavbarRoute = ({ icon, children, href }: Props) => {
    return (
        <NavRoute href={href}>
            {icon}
            <span>{children}</span>
        </NavRoute>
    );
};

export default NavbarRoute;
