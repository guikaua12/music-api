'use client';
import styled from 'styled-components';

export const Container = styled.div`
    display: flex;
    justify-content: space-between;
    background-color: #1a1a1a;
    max-width: 500px;
    width: 100%;
    border-radius: 0.75rem;
`;

export const SearchInput = styled.input`
    width: 100%;
    background-color: transparent;
    outline: none;
    border: 0;
    padding: 0 1rem;
    color: #8c8c8c;
    font-size: 0.75rem;

    &::placeholder {
        color: #8c8c8c;
    }
`;

export const SearchButton = styled.button`
    background-color: transparent;
    border: 0;
    outline: 0;
    color: #bfbfbf;
    padding: 0.25rem 0.5rem;
    cursor: pointer;
`;
