import React, { ComponentProps } from 'react';
import { Container, SearchButton, SearchInput } from './SearchBar.styles';
import { PiMagnifyingGlassBold } from 'react-icons/pi';

interface Props extends ComponentProps<'div'> {}

const SearchBar = ({ children, ...props }: Props) => {
    return (
        <Container>
            <SearchInput placeholder={children as string}></SearchInput>
            <SearchButton>
                <PiMagnifyingGlassBold size={20} />
            </SearchButton>
        </Container>
    );
};

export default SearchBar;
