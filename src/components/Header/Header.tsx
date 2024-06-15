import React from 'react';
import { Container } from '@/components/Header/Header.styles';
import Button from '@/components/Button/Button';
import { PiCloudArrowUpBold } from 'react-icons/pi';
import SearchBar from '@/components/SearchBar/SearchBar';

const Header = () => {
    return (
        <Container>
            <SearchBar>Procure por uma música</SearchBar>
            <Button>
                <PiCloudArrowUpBold size={20} color="white" />
            </Button>
        </Container>
    );
};

export default Header;
