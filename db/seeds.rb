5.times do
  article.create({
    dish: Faker::Food.dish,
    measurement: Faker::Food.measurement
    })
end
