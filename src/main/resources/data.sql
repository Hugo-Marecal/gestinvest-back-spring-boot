BEGIN;
INSERT INTO "category" ("name") VALUES
('crypto'),
('stock')
ON CONFLICT ("name") DO NOTHING;

 COMMIT;

 BEGIN;

INSERT INTO "asset" ("name", "symbol", "category_id") VALUES
('Bitcoin', 'BTC', 1),
('Ethereum', 'ETH', 1),
('BNB', 'BNB', 1),
('Solana', 'SOL', 1),
('Ripple', 'XRP', 1),
('Dogecoin', 'DOGE', 1),
('Cardano', 'ADA', 1),
('Avalanche', 'AVAX', 1),
('Shiba Inu', 'SHIB', 1),
('Polkadot', 'DOT', 1),
('Chainlink', 'LINK', 1),
('Tron', 'TRX', 1),
('Polygon', 'MATIC', 1),
('Uniswap', 'UNI', 1),
('Aave', 'AAVE', 1),
('Cosmos', 'ATOM', 1),
('Tezos', 'XTZ', 1),
('Chiliz', 'CHZ', 1),
('Iota', 'IOTA', 1),
('Toncoin', 'TON', 1),
('Bitcoin Cash', 'BCH', 1),
('Internet Computer', 'ICP', 1),
('Near Protocol', 'NEAR', 1),
('Aptos', 'APT', 1),
('Litecoin', 'LTC', 1),
('Ethereum Classic', 'ETC', 1),
('Arbitrum', 'ARB', 1),
('Immutable', 'IMX', 1),
('Render', 'RENDER', 1),
('Hedera', 'HBAR', 1),
('OKB', 'OKB', 1),
('Stellar', 'XLM', 1),
('Optimism', 'OP', 1),
('Cronos', 'CRO', 1),
('Bittensor', 'TAO', 1),
('Pepe', 'PEPE', 1),
('THORChain', 'RUNE', 1),
('Theta Network', 'THETA', 1),
('Fantom', 'FTM', 1),
('Maker', 'MKR', 1),
('Mantle', 'MNT', 1),
('Fetch.ai', 'FET', 1),
('Ocean protocol', 'OCEAN', 1),
('Floki', 'FLOKI', 1),
('Flow', 'FLOW', 1),
('Gala', 'GALA', 1),
('Beam', 'BEAM', 1),
('Bonk', 'BONK', 1),
('Terra classic', 'LUNC', 1),
('Terra', 'LUNA', 1),
('SushiSwap', 'SUSHI', 1),
('NEST Protocol', 'NEST', 1)
ON CONFLICT ("name") DO NOTHING;
--

INSERT INTO "asset" ("name", "symbol", "category_id", "local") VALUES
('NVIDIA Corporation', 'NVDA', 2, 'NASDAQ'),
('Apple Inc.', 'AAPL', 2, 'NASDAQ'),
('Microsoft Corporation', 'MSFT', 2, 'NASDAQ'),
('Amazon.com Inc.', 'AMZN', 2, 'NASDAQ'),
('Meta Platforms Inc.', 'META', 2, 'NASDAQ'),
('Alphabet Inc.', 'GOOGL', 2, 'NASDAQ'),
('Tesla Inc.', 'TSLA', 2, 'NASDAQ'),
('Alibaba Group Holding Limited', 'BABA', 2, 'NYSE'),
('Tencent Holdings Limited', 'TCEHY', 2, 'OTC'),
('JPMorgan Chase & Co.', 'JPM', 2, 'NYSE'),
('Johnson & Johnson', 'JNJ', 2, 'NYSE'),
('Visa Inc.', 'V', 2, 'NYSE'),
('Procter & Gamble Company', 'PG', 2, 'NYSE'),
('Walmart Inc.', 'WMT', 2, 'NYSE'),
('Mastercard Incorporated', 'MA', 2, 'NYSE'),
('UnitedHealth Group Incorporated', 'UNH', 2, 'NYSE'),
('Home Depot Inc.', 'HD', 2, 'NYSE'),
('Bank of America Corporation', 'BAC', 2, 'NYSE'),
('Intel Corporation', 'INTC', 2, 'NASDAQ'),
('Verizon Communications Inc.', 'VZ', 2, 'NYSE'),
('Coca-Cola Company', 'KO', 2, 'NYSE'),
('Pfizer Inc.', 'PFE', 2, 'NYSE'),
('Netflix Inc.', 'NFLX', 2, 'NASDAQ'),
('Walt Disney Company', 'DIS', 2, 'NYSE'),
('AT&T Inc.', 'T', 2, 'NYSE'),
('Cisco Systems Inc.', 'CSCO', 2, 'NASDAQ'),
('Abbott Laboratories', 'ABT', 2, 'NYSE'),
('Merck & Co. Inc.', 'MRK', 2, 'NYSE'),
('Salesforce.com Inc.', 'CRM', 2, 'NYSE'),
('Adobe Inc.', 'ADBE', 2, 'NASDAQ'),
('Oracle Corporation', 'ORCL', 2, 'NYSE'),
('IBM Corporation', 'IBM', 2, 'NYSE'),
('3M Company', 'MMM', 2, 'NYSE'),
('Honeywell International Inc.', 'HON', 2, 'NASDAQ'),
('Caterpillar Inc.', 'CAT', 2, 'NYSE'),
('General Electric Company', 'GE', 2, 'VIE'),
('Goldman Sachs Group Inc.', 'GS', 2, 'NYSE'),
('Exxon Mobil Corporation', 'XOM', 2, 'NYSE'),
('Boeing Company', 'BA',2, 'NYSE'),
('General Motors Company', 'GM', 2, 'NYSE'),
('Ford Motor Company', 'F', 2, 'NYSE'),
('Delta Air Lines Inc.', 'DAL', 2, 'NYSE'),
('Southwest Airlines Co.', 'LUV', 2, 'NYSE'),
('American Airlines Group Inc.', 'AAL', 2, 'NASDAQ'),
('United Airlines Holdings Inc.', 'UAL', 2, 'NASDAQ'),
('FedEx Corporation', 'FDX', 2, 'NYSE'),
('United Parcel Service Inc.', 'UPS', 2, 'NYSE'),
('Carnival Corporation & plc', 'CCL', 2, 'NYSE'),
('Royal Caribbean Group', 'RCL', 2, 'NYSE'),
('Norwegian Cruise Line Holdings Ltd.', 'NCLH', 2, 'NYSE'),
('Marriott International Inc.', 'MAR', 2, 'NASDAQ'),
('Hilton Worldwide Holdings Inc.', 'HLT', 2, 'NYSE'),
('Booking Holdings Inc.', 'BKNG', 2, 'NASDAQ'),
('Expedia Group Inc.', 'EXPE', 2, 'NASDAQ'),
('Trip.com Group Limited', 'TCOM', 2, 'NASDAQ'),
('Uber Technologies Inc.', 'UBER', 2, 'NYSE'),
('Lyft Inc.', 'LYFT', 2, 'NASDAQ'),
('Airbnb Inc.', 'ABNB', 2, 'NASDAQ'),
('DoorDash Inc.', 'DASH', 2, 'NASDAQ'),
('Starbucks Corporation', 'SBUX', 2, 'NASDAQ'),
('Yum! Brands Inc.', 'YUM', 2, 'NYSE'),
('Chipotle Mexican Grill Inc.', 'CMG', 2, 'NYSE'),
('McDonald''s Corporation', 'MCD', 2, 'NYSE'),
('Wendy''s Company', 'WEN', 2, 'NASDAQ'),
('Domino''s Pizza Inc.', 'DPZ', 2, 'NYSE'),
('Papa John''s International Inc.', 'PZZA', 2, 'NASDAQ'),
('Yum China Holdings Inc.', 'YUMC', 2, 'NYSE'),
('Beyond Meat Inc.', 'BYND', 2, 'NASDAQ'),
('Tyson Foods Inc.', 'TSN', 2, 'NYSE'),
('PepsiCo Inc.', 'PEP', 2, 'NASDAQ'),
('Monster Beverage Corporation', 'MNST', 2, 'NASDAQ'),
('Keurig Dr Pepper Inc.', 'KDP', 2, 'NASDAQ'),
('Anheuser-Busch InBev SA/NV', 'BUD', 2, 'NYSE'),
('Constellation Brands Inc.', 'STZ', 2, 'NYSE'),
('ACCOR', 'AC.PA', 2, 'VIE'),
('AIR LIQUIDE', 'AI.PA', 2, 'VIE'),
('AIRBUS', 'AIR.PA', 2, 'VIE'),
('ALSTOM', 'ALO.PA', 2, 'VIE'),
('ATOS', 'ATO.PA', 2, 'VIE'),
('AXA', 'CS.PA', 2 , 'VIE'),
('BNP PARIBAS', 'BNP.PA', 2 , 'VIE'),
('BOUYGUES', 'EN.PA', 2 , 'VIE'),
('CAPGEMINI', 'CAP.PA', 2 , 'VIE'),
('CARREFOUR', 'CA.PA', 2 , 'VIE'),
('CREDIT AGRICOLE', 'ACA.PA', 2 , 'VIE'),
('DANONE', 'BN.PA', 2 , 'VIE'),
('DASSAULT SYSTEMES', 'DSY.PA', 2 , 'VIE'),
('ENGIE', 'ENGI.PA', 2 , 'VIE'),
('ESSILORLUXOTTICA', 'EL.PA', 2 , 'VIE'),
('HERMES INTERNATIONAL', 'RMS.PA', 2 , 'VIE'),
('KERING', 'KER.PA', 2 , 'VIE'),
('L''OREAL', 'OR.PA', 2 , 'VIE'),
('LVMH', 'MC.PA', 2 , 'VIE'),
('MICHELIN', 'ML.PA', 2 , 'VIE'),
('ORANGE', 'ORA.PA', 2 , 'VIE'),
('PERNOD RICARD', 'RI.PA', 2 , 'VIE'),
('PUBLICIS GROUPE', 'PUB.PA', 2 , 'VIE'),
('RENAULT', 'RNO.PA', 2 , 'VIE'),
('SAFRAN', 'SAF.PA', 2 , 'VIE'),
('SAINT-GOBAIN', 'SGO.PA', 2 , 'VIE'),
('SANOFI', 'SAN.PA', 2 , 'VIE'),
('SCHNEIDER ELECTRIC', 'SU.PA', 2 , 'VIE'),
('SOCIETE GENERALE', 'GLE.PA', 2 , 'VIE'),
('STMICROELECTRONICS', 'STMPA.PA', 2 , 'VIE'),
('TECHNIPFMC', 'FTI', 2 , 'VIE'),
('TOTAL', 'TTE.PA', 2 , 'VIE'),
('UNIBAIL-RODAMCO-WESTFIELD', 'URW.PA', 2 , 'VIE'),
('VEOLIA ENVIRONNEMENT', 'VIE.PA', 2 , 'VIE'),
('VINCI', 'DG.PA', 2 , 'VIE'),
('VIVENDI', 'VIV.PA', 2 , 'VIE'),
('WORLDLINE', 'WLN.PA', 2 , 'VIE')
ON CONFLICT ("name") DO NOTHING;

INSERT INTO "trading_operation_type" ("name") VALUES
('buy'),
('sell')
ON CONFLICT ("name") DO NOTHING;

COMMIT;