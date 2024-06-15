import React from 'react';
import Image from 'next/image';
import { Container, LogoContainer } from './Sidebar.styles';
import Navbar from '@/components/Navbar/Navbar';

const Sidebar = () => {
    return (
        <Container>
            <LogoContainer>
                <Image src="/logo.svg" alt="Logo" width={128} height={128} />
            </LogoContainer>
            <Navbar />
        </Container>
    );
};

export default Sidebar;
