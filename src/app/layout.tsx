import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import { Container, RightSide } from './layout.styles';
import Sidebar from '@/components/Sidebar/Sidebar';
import ThemeProvider from '@/providers/ThemeProvider';
import Header from '@/components/Header/Header';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
    title: 'Music',
    description: 'Music website',
};

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="pt-br">
            <body className={inter.className}>
                <ThemeProvider>
                    <Container>
                        <Sidebar />
                        <RightSide>
                            <Header />
                            <main>{children}</main>
                        </RightSide>
                    </Container>
                </ThemeProvider>
            </body>
        </html>
    );
}
