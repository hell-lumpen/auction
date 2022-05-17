import type { NextPage } from 'next'
import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'
import { Button } from '@mui/material';
import { ButtonGroup } from '@mui/material';
import { Grid } from '@mui/material';
const Home: NextPage = () => {
  return (
    <div className={styles.container}>
      <Head>
        <title>Void auction</title>
        <meta name="description" content="void auction" />
        <link rel="icon" href="/favicon.ico" />,
        <Grid container justifyContent="flex-end">
          <a href="\">
          <Button variant="contained">Регистрация</Button>
          </a>
          <a href="\sing_in">
          <Button variant="contained">Вход</Button>
          </a>
        </Grid>
      </Head>

      <main className={styles.main}>
        <h1 className={styles.title}>
          Дoбро пожаловать на аукционную платформу <a href="/auctions">Void!</a>
        </h1>

        <p className={styles.description}>
          Попробуй, и ты ощутишь ни с чем не сравнимое наслаждения от использования нашей платформы. Просто{' '}
          <a href="https://nextjs.org"><b>зарегистрируйся</b>!</a>
        </p>

        <h2 className={styles.title}>
          Наши преимущества
        </h2>
        <div className={styles.grid}>
            
            <div className={styles.card}>
            <h2>Отсутствие комиссий &rarr;</h2>
            <p>Мы не берем никакие комиссии с лотов и ставок.</p>
            </div>

            <div className={styles.card}>
            <h2>Простой интерфейс &rarr;</h2>
            <p>Все, чтобы сразу начать торговать!</p>
            </div>

            <div className={styles.card}>
            <h2>Прозрачность &rarr;</h2>
            <p>0 комиссий, 0 техподдержки, 0 понимания и фунт презрения! Все, чтобы вы прочувствовали дух нашей платформы!</p>
            </div>

            <div className={styles.card}>
            <h2>Техподдержка &rarr;</h2>
            <p>
              Каждое обращение рассматривается максимально быстро! Всего 90 рабочих дней и мы вернемся к вам с отказом!
            </p>
            </div>
        </div>
      </main>

      <footer className={styles.footer}>
          Powered by Void team
      </footer>
    </div>
  )
}

export default Home
