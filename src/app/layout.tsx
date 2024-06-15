import type { Metadata } from 'next';
import { Montserrat } from 'next/font/google';
import './globals.css';
import { Container, RightSide } from './layout.styles';
import Sidebar from '@/components/Sidebar/Sidebar';
import ThemeProvider from '@/providers/ThemeProvider';
import Header from '@/components/Header/Header';

const montserrat = Montserrat({ subsets: ['latin'] });

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
            <body className={montserrat.className}>
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
