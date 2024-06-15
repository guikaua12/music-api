'use client';
import styled from 'styled-components';
import Link from 'next/link';

export const NavRoute = styled(Link)`
    padding: 8px 40px;
    color: ${(props) => props.theme.neutral5};
    display: flex;
    align-items: center;
    gap: 1rem;
    text-decoration: none;

    &:hover {
        background-color: ${(props) => props.theme.neutral2};
    }
`;
